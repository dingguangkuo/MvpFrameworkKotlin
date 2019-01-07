package com.guangkuo.mvpfwk.module.user.login

import com.guangkuo.mvpfwk.base.BasePresenter
import com.guangkuo.mvpfwk.data.bean.User
import com.guangkuo.mvpfwk.data.remote.ApiServerManager
import com.guangkuo.mvpfwk.data.remote.error.ApiException
import com.guangkuo.mvpfwk.data.remote.observer.MyObserver
import javax.inject.Inject

class LoginPresenter @Inject constructor()
    : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    override fun login(account: String, password: String) {
        ApiServerManager.getInstance()
                .login(account, password,
                        object : MyObserver<User, LoginContract.View>(mView!!) {
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
