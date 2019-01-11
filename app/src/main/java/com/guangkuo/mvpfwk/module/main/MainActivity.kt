package com.guangkuo.mvpfwk.module.main

import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity
import com.guangkuo.mvpfwk.utils.JniUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
        sample_text.text = JniUtils().loadCString()
    }

    override fun bindListener() {
    }
}
