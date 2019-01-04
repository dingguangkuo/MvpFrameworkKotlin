package com.guangkuo.mvpfwk.module.user

import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_user.*
import java.util.concurrent.TimeUnit

class UserActivity : BaseActivity<UserContract.View, UserPresenter>(), UserContract.View {
    override val layoutId: Int
        get() = R.layout.activity_user

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
    }

    override fun bindListener() {
        RxView
                .clicks(btnLogin)
                .debounce(2L, TimeUnit.SECONDS)// 两秒钟之内只取一个点击事件，防抖操作
                .subscribe {
                    mPresenter.login("username", "password")
                }
    }
}
