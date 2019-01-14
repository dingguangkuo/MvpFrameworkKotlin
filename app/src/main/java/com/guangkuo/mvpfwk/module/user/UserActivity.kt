package com.guangkuo.mvpfwk.module.user

import com.guangkuo.mvpfwk.R
import com.guangkuo.mvpfwk.base.BaseActivity
import com.guangkuo.mvpfwk.base.CommonFragmentPagerAdapter
import com.guangkuo.mvpfwk.module.user.login.LoginFragment
import com.guangkuo.mvpfwk.module.user.register.RegisterFragment
import com.guangkuo.mvpfwk.utils.TransformUtils
import kotlinx.android.synthetic.main.activity_user.*
import com.guangkuo.mvpfwk.transformer.FlipHorizontalTransformer

class UserActivity : BaseActivity<UserContract.View, UserPresenter>(), UserContract.View {
    private val horizontalTransformer = FlipHorizontalTransformer()

    override val layoutId: Int
        get() = R.layout.activity_user

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
        vpContainer.disableScroll()
        val cfpAdapter = CommonFragmentPagerAdapter(supportFragmentManager)
        cfpAdapter.addFragment(LoginFragment.newInstance())
        cfpAdapter.addFragment(RegisterFragment.newInstance())
        vpContainer.adapter = cfpAdapter
    }

    override fun bindListener() {
    }

    fun switchView(item: Int) {
        if (1 == item) {
            TransformUtils.forward(vpContainer, horizontalTransformer)
        } else {
            TransformUtils.reverse(vpContainer, horizontalTransformer)
        }
        vpContainer.currentItem = item
    }
}
