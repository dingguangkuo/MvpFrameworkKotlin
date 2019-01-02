package com.guangkuo.mvpfwk.data.remote.observer

import com.guangkuo.mvpfwk.base.BaseContract
import com.guangkuo.mvpfwk.data.remote.error.ApiException
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

/**
 * 对服务器返回异常、错误做统一处理
 */
abstract class MyObserver<T, V : BaseContract.BaseView>(private val mView: V) : ErrorObserver<T>() {

    override fun onSubscribe(@NonNull d: Disposable) {
        mView.showLoading()
    }

    override fun onNext(@NonNull result: T) {
        mView.onLoadSuccess(result)
    }

    override fun onError(ex: ApiException) {
        mView.stopLoading()
        mView.onLoadFailed(ex)
    }

    override fun onComplete() {
        mView.stopLoading()
    }
}
