package com.guangkuo.mvpfwk.base

import android.os.Bundle
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
import javax.inject.Inject

/**
 * BaseFragment
 *
 * @param <P> Presenter
 */
abstract class BaseFragment<P : BaseContract.BasePresenter<BaseContract.BaseView>> : Fragment(), BaseContract.BaseView {
    @Inject
    protected lateinit var mPresenter: P
    protected lateinit var mFragmentComponent: FragmentComponent
    private var mRootView: View? = null

    protected abstract val layoutId: Int

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
        mPresenter.attachView(this)
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

    companion object {
        private const val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    }
}
