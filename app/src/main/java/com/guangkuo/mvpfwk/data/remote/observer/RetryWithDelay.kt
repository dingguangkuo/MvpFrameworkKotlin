package com.guangkuo.mvpfwk.data.remote.observer

import com.guangkuo.mvpfwk.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * 失败重试
 *
 * @param maxRetries       最多重试次数
 * @param retryDelayMillis 重试时间间隔
 */
class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Int) : Function<Observable<out Throwable>, Observable<Any>> {
    private var retryCount: Int = 0

    override fun apply(@NonNull observable: Observable<out Throwable>): Observable<Any> {
        return observable.flatMap<Any>(Function<Throwable, ObservableSource<*>> { throwable ->
            if (++retryCount <= maxRetries) {
                LogUtils.i("Get error, it will try after $retryDelayMillis millisecond, retry count $retryCount")
                return@Function Observable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)
            }
            Observable.error<Any>(throwable)
        })
    }
}