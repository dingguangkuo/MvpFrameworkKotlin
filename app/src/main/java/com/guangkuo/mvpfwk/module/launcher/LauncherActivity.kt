package com.guangkuo.mvpfwk.module.launcher

import android.content.Intent
import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity
import com.guangkuo.mvpfwk.module.user.UserActivity
import com.guangkuo.mvpfwk.utils.RxUtils

/**
 * 程序入口页面
 */
class LauncherActivity : BaseActivity<LauncherContract.View, LauncherPresenter>(), LauncherContract.View {
    override val layoutId: Int
        get() = R.layout.activity_entry

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
    }

    override fun bindListener() {
        RxUtils.countdown(3L, object : RxUtils.CountdownListener {
            override fun countdownEnd() {
                startActivity(Intent(this@LauncherActivity, UserActivity::class.java))
            }
        })
    }
}
