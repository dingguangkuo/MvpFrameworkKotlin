package com.guangkuo.mvpfwk.module.user.register

import com.guangkuo.mvpfwk.base.BaseContract

/**
 *
 * Created by Guangkuo on 2019/1/14.
 */
interface RegisterContract {
    interface View : BaseContract.BaseView

    interface Presenter : BaseContract.BasePresenter<View> {
        fun register(account: String, password: String)
    }
}