package com.guangkuo.mvpfwk.base

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.app.App
import com.guangkuo.mvpfwk.data.remote.error.ApiException
import com.guangkuo.mvpfwk.di.components.DaggerFragmentComponent
import com.guangkuo.mvpfwk.di.components.FragmentComponent
import com.guangkuo.mvpfwk.di.modules.FragmentModule
import com.guangkuo.mvpfwk.utils.NetworkUtils
import com.guangkuo.mvpfwk.utils.ToastUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * BaseFragment
 *
 * @param <P> Presenter
 */
abstract class BaseFragment<in V : BaseContract.BaseView, P : BaseContract.BasePresenter<V>> : Fragment(), BaseContract.BaseView {
    protected var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    @Inject
    protected lateinit var mActivity: Activity
    @Inject
    protected lateinit var mPresenter: P
    protected lateinit var mFragmentComponent: FragmentComponent
    private var mRootView: View? = null

    protected abstract val layoutId: Int

    // 权限申请相关
    private var mRequestPermissionCallback: RequestPermissionCallback? = null

    protected abstract fun initInjector()

    protected abstract fun initView(view: View?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragmentComponent()
        initInjector()
        attachView()
        val isHidden = savedInstanceState?.getBoolean(STATE_SAVE_IS_HIDDEN)
        val ft = fragmentManager?.beginTransaction()
        if (isHidden == true) {
            ft?.hide(this)
        } else {
            ft?.show(this)
        }
        ft?.commit()
        if (!NetworkUtils.isConnected()) {
            showNoNet()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflaterView(inflater, container)
        initView(mRootView)
        return mRootView
    }

    override fun onDestroy() {
        super.onDestroy()
        detachView()
        mCompositeDisposable.clear()
    }

    override fun showLoading() {}

    override fun stopLoading() {}

    override fun onLoadFailed(ex: ApiException) {}

    override fun <T> onLoadSuccess(result: T) {}

    override fun showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection)
    }

    /**
     * 初始化FragmentComponent
     */
    private fun initFragmentComponent() {
        val activity = activity
        if (activity != null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .appComponent(App.getAppComponent((activity.application as App)))
                    .fragmentModule(FragmentModule(this))
                    .build()
        }
    }

    /**
     * 贴上view
     */
    private fun attachView() {
        @Suppress("UNCHECKED_CAST")
        mPresenter.attachView(view = this as V)
    }

    /**
     * 分离view
     */
    private fun detachView() {
        mPresenter.detachView()
    }

    /**
     * 设置View
     *
     * @param inflater
     * @param container
     */
    private fun inflaterView(inflater: LayoutInflater, container: ViewGroup?) {
        if (mRootView == null) {
            mRootView = inflater.inflate(layoutId, container, false)
        }
    }

    override fun jumpToLogin() {
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
            ActivityCompat.requestPermissions(mActivity, permissions, requestCode)
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

    companion object {
        private const val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    }
}
