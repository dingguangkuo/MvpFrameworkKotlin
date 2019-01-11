package com.guangkuo.mvpfwk.module.main

import com.guangkuo.mvpfwk.base.BaseContract

interface MainContract {
    interface View : BaseContract.BaseView

    interface Presenter : BaseContract.BasePresenter<View> {
        //
    }
}
