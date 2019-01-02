package com.guangkuo.mvpfwk.base

import com.guangkuo.mvpfwk.data.remote.error.ApiException

/**
 * 协议类
 */
interface BaseContract {
    interface BasePresenter<in V : BaseContract.BaseView> {
        fun attachView(view: V)

        fun detachView()
    }

    interface BaseView {
        /**
         * 显示加载框
         */
        fun showLoading()

        /**
         * 隐藏加载框
         */
        fun stopLoading()

        /**
         * 加载失败
         *
         * @param ex
         */
        fun onLoadFailed(ex: ApiException)

        /**
         * 加载成功
         *
         * @param result 服务器返回数据
         */
        fun <T> onLoadSuccess(result: T)

        /**
         * 显示当前网络不可用
         */
        fun showNoNet()

        /**
         * 跳转到登录页面
         */
        fun jumpToLogin()
    }
}
