package com.guangkuo.mvpfwk.module.user

import android.content.Context
import android.content.Intent
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity

class UserActivity : BaseActivity<UserContract.View, UserPresenter>(), UserContract.View {
    override val layoutId: Int
        get() = R.layout.activity_user

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
    }

    override fun bindListener() {
    }
}
