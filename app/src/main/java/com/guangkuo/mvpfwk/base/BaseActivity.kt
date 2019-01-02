package com.guangkuo.mvpfwk.base

import android.os.Build
import android.os.Bundle
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
import javax.inject.Inject

/**
 * BaseActivity
 *
 * @param <P> Presenter
 */
abstract class BaseActivity<P : BasePresenter<BaseContract.BaseView>> : AppCompatActivity(), BaseContract.BaseView {
    @Inject
    lateinit var mPresenter: P
    protected lateinit var mActivityComponent: ActivityComponent
    protected var mToolbar: Toolbar? = null

    protected abstract val layoutId: Int

    /**
     * 是否显示返回键
     */
    protected var isShowHomeAsUp: Boolean = false

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
    }

    private fun attachView() {
        mPresenter.attachView(this)
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
}
