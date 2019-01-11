package com.guangkuo.mvpfwk.module.launcher

import com.guangkuo.mvpfwk.base.BaseContract

interface LauncherContract {
    interface View : BaseContract.BaseView

    interface Presenter : BaseContract.BasePresenter<View> {
        //
    }
}
