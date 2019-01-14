package com.guangkuo.mvpfwk.module.user.register

import android.content.Intent
import android.view.View
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseFragment
import com.guangkuo.mvpfwk.module.main.MainActivity
import com.guangkuo.mvpfwk.module.user.UserActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * 注册
 * Created by Guangkuo on 2019/1/14.
 */
class RegisterFragment : BaseFragment<RegisterContract.View, RegisterPresenter>(), RegisterContract.View {
    override val layoutId: Int
        get() = R.layout.fragment_register

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun initView(view: View?) {
    }

    override fun bindListener() {
        var disposable = RxView.clicks(btnRegister).subscribe({
            (activity as UserActivity).switchView(0)
        })
        mCompositeDisposable.add(disposable)
        disposable = RxView.clicks(btnToMain).subscribe({
            startActivity(Intent(activity, MainActivity::class.java))
        })
        mCompositeDisposable.add(disposable)
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}