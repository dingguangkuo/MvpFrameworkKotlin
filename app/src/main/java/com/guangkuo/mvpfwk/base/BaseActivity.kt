package com.guangkuo.mvpfwk.base

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.app.App
import com.guangkuo.mvpfwk.callback.RequestPermissionCallback
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
    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    @Inject
    lateinit var mPresenter: P
    protected lateinit var mActivityComponent: ActivityComponent

    protected var tvToolbarTitle: TextView? = null
    protected var ivToolbarLeft: ImageView? = null
    protected var ivToolbarRight: ImageView? = null

    protected abstract val layoutId: Int

    // 权限申请相关
    private var mRequestPermissionCallback: RequestPermissionCallback? = null

    /**
     * 是否使用自定义标题
     *
     * return true: 使用，false: 不使用
     */
    open fun useCustomTitle(): Boolean {
        return false
    }

    protected abstract fun initInjector()

    open fun beforeInit() {
        if (useCustomTitle()) {
            // 在 setContentView 之前请求窗口特性
            requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        }
    }

    protected abstract fun initView()

    protected abstract fun bindListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityComponent()
        beforeInit()// 界面初始化前期准备
        setContentView(layoutId)
        // 设置自定义标题的布局资源
        if (useCustomTitle()) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title)
            initTitle()// 初始化Title
        }
        initInjector()
        attachView()
        initView()
        bindListener()
        if (!NetworkUtils.isConnected()) {
            showNoNet()
        }
    }

    /**
     * 初始化Title
     */
    private fun initTitle() {
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle)
        ivToolbarLeft = findViewById(R.id.ivToolbarLeft)
        ivToolbarRight = findViewById(R.id.ivToolbarRight)

        tvToolbarTitle?.text = title
        ivToolbarLeft?.setOnClickListener { leftClickListener() }
        ivToolbarRight?.setOnClickListener { rightClickListener() }
    }

    /**
     * Title左边按钮监听（此方法可以覆盖）
     */
    protected fun leftClickListener() {
        finish()// 默认返回
    }

    /**
     * Title右边按钮监听（此方法需要覆盖）
     */
    protected fun rightClickListener() {}

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
