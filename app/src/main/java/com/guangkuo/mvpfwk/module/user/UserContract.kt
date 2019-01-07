package com.guangkuo.mvpfwk.module.user

import com.guangkuo.mvpfwk.base.BaseContract

interface UserContract {
    interface View : BaseContract.BaseView

    interface Presenter : BaseContract.BasePresenter<View>
}
