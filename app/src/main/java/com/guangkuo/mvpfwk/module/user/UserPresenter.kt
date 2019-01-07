package com.guangkuo.mvpfwk.module.user

import com.guangkuo.mvpfwk.base.BasePresenter
import com.guangkuo.mvpfwk.data.bean.User
import com.guangkuo.mvpfwk.data.remote.ApiServerManager
import com.guangkuo.mvpfwk.data.remote.error.ApiException
import com.guangkuo.mvpfwk.data.remote.observer.MyObserver
import javax.inject.Inject

class UserPresenter @Inject constructor() : BasePresenter<UserContract.View>(), UserContract.Presenter
