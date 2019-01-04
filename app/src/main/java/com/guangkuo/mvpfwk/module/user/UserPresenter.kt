package com.guangkuo.mvpfwk.module.user

import com.guangkuo.mvpfwk.base.BasePresenter
import com.guangkuo.mvpfwk.data.bean.User
import com.guangkuo.mvpfwk.data.remote.ApiServerManager
import com.guangkuo.mvpfwk.data.remote.error.ApiException
import com.guangkuo.mvpfwk.data.remote.observer.MyObserver
import javax.inject.Inject

class UserPresenter @Inject constructor() : BasePresenter<UserContract.View>(), UserContract.Presenter {

    override fun login(account: String, password: String) {
        ApiServerManager.getInstance()
                .login(account, password,
                        object : MyObserver<User, UserContract.View>(mView!!) {
                            override fun onNext(result: User) {
                                super.onNext(result)
                                mView?.onLoadSuccess(result)
                            }

                            override fun onError(ex: ApiException) {
                                super.onError(ex)
                                mView?.onLoadFailed(ex)
                            }
                        })
    }
}
