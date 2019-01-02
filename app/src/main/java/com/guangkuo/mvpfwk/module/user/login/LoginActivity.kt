package com.guangkuo.mvpfwk.module.user.login

import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initInjector() {
    }

    override fun initView() {
    }

    override fun bindListener() {
    }
}
