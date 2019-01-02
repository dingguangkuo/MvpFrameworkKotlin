package com.guangkuo.mvpfwk.module.user.login

import com.guangkuo.mvpfwk.base.BaseContract

interface LoginContract {
    interface View : BaseContract.BaseView

    interface Presenter : BaseContract.BasePresenter<View> {
        /**
         * 登录用户
         *
         * @param account  用户名
         * @param password 密码
         */
        fun login(account: String, password: String)
    }
}
