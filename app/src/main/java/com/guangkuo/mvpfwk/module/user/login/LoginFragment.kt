package com.guangkuo.mvpfwk.module.user.login

import android.view.View
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseFragment

class LoginFragment : BaseFragment<LoginContract.View, LoginPresenter>(), LoginContract.View {
    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun initInjector() {
    }

    override fun initView(view: View?) {
    }
}
