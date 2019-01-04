package com.guangkuo.mvpfwk.base

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.app.App
import com.guangkuo.mvpfwk.data.remote.error.ApiException
import com.guangkuo.mvpfwk.di.components.ActivityComponent
import com.guangkuo.mvpfwk.di.components.DaggerActivityComponent
import com.guangkuo.mvpfwk.di.modules.ActivityModule
import com.guangkuo.mvpfwk.utils.NetworkUtils
import com.guangkuo.mvpfwk.utils.ToastUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * BaseActivity
 *
 * @param <P> Presenter
 */
abstract class BaseActivity<V : BaseContract.BaseView, P : BasePresenter<V>> : AppCompatActivity(), BaseContract.BaseView {
    protected var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    @Inject
    lateinit var mPresenter: P
    protected lateinit var mActivityComponent: ActivityComponent
    protected var mToolbar: Toolbar? = null

    protected abstract val layoutId: Int

    /**
     * 是否显示返回键
     */
    protected var isShowHomeAsUp: Boolean = false

    // 权限申请相关
    private var mRequestPermissionCallback: RequestPermissionCallback? = null

    protected abstract fun initInjector()

    protected abstract fun initView()

    protected abstract fun bindListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityComponent()
        setContentView(layoutId)
        initInjector()
        initToolBar()
        attachView()
        initView()
        bindListener()
        if (!NetworkUtils.isConnected()) {
            showNoNet()
        }
    }

    override fun showLoading() {}

    override fun stopLoading() {}

    override fun onLoadFailed(ex: ApiException) {
        ToastUtils.showShort(ex.msg)
    }

    override fun <T> onLoadSuccess(result: T) {}

    override fun onDestroy() {
        super.onDestroy()
        detachView()
        mCompositeDisposable.clear()
    }

    private fun attachView() {
        @Suppress("UNCHECKED_CAST")
        mPresenter.attachView(view = this as V)
    }

    /**
     * 跳转到登录页面
     */
    override fun jumpToLogin() {
    }

    override fun showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection)
    }

    protected fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    /**
     * 分离view
     */
    private fun detachView() {
        mPresenter.detachView()
    }

    /**
     * 初始化toolbar
     */
    protected fun initToolBar() {
        mToolbar = findViewById(R.id.toolbar)
        if (mToolbar == null) {
            throw NullPointerException("toolbar can not be null")
        }
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isShowHomeAsUp)
        // toolbar除掉阴影
        supportActionBar?.elevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar?.elevation = 0f
        }
    }

    private fun initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as App).appComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    /**
     * 请求权限
     * @param requestCode 请求码
     * @param permissions 权限数组
     */
    protected fun requestPermissions(requestCode: Int, permissions: Array<String>, callback: RequestPermissionCallback) {
        mRequestPermissionCallback = callback

        // 版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        } else {
            mRequestPermissionCallback?.onGranted(permissions.asList())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val granted: MutableList<String> = ArrayList()
        val denied: MutableList<String> = ArrayList()
        val deniedForever: MutableList<String> = ArrayList()

        for (i in 0..(permissions.size - 1)) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[i]) {
                granted.add(permissions[i])
            } else {
                denied.add(permissions[i])
                // 选择不再提醒
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        !shouldShowRequestPermissionRationale(permissions[i])) {
                    deniedForever.add(permissions[i])
                }
            }
        }
        // 调用回调方法
        mRequestPermissionCallback?.onGranted(granted)
        mRequestPermissionCallback?.onDenied(deniedForever, denied)
    }

    /**
     * 是否允许
     */
    internal fun isGranted(grantResult: Int): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PackageManager.PERMISSION_GRANTED == grantResult
    }
}
