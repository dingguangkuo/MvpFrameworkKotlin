package com.guangkuo.mvpfwk.module.user.register

import com.guangkuo.mvpfwk.base.BasePresenter
import javax.inject.Inject

/**
 *
 * Created by Guangkuo on 2019/1/14.
 */
class RegisterPresenter @Inject constructor()
    : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {
    override fun register(account: String, password: String) {
    }
}