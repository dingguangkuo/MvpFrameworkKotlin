package com.guangkuo.mvpfwk.data.remote.observer

import com.guangkuo.mvpfwk.data.remote.error.ApiException
import io.reactivex.Observer

abstract class ErrorObserver<T> : Observer<T> {
    override fun onError(e: Throwable) {
        if (e is ApiException) {
            // TODO: 需要做统一处理的错误代码放在这里处理
            onError(e)
        } else {
            // 其他异常的处理
            onError(ApiException(e, -100))
        }
        e.printStackTrace()
    }

    /**
     * 错误回调
     */
    internal abstract fun onError(ex: ApiException)
}