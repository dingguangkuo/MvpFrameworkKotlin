package com.guangkuo.mvpfwk.data.remote.error

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * Http 请求错误处理
 */
class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
    override fun apply(@NonNull throwable: Throwable): Observable<T> {
        return Observable.error(ExceptionEngine.handleException(throwable)) // ExceptionEngine为处理异常的驱动器
    }
}
