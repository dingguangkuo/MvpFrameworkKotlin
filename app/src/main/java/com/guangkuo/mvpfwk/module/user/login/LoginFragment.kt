package com.guangkuo.mvpfwk.module.user.login

import android.view.View
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseFragment
import com.guangkuo.mvpfwk.module.user.UserActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * 登录
 * Created by Guangkuo on 2019/1/14.
 */
class LoginFragment : BaseFragment<LoginContract.View, LoginPresenter>(), LoginContract.View {

    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun initView(view: View?) {
    }

    override fun bindListener() {
        val disposable = RxView.clicks(btnLogin)
                .subscribe({
                    (activity as UserActivity).switchView(1)
                })
        mCompositeDisposable.add(disposable)
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
