package com.guangkuo.mvpfwk.utils

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Rx工具类
 */
class RxUtils {
    /**
     * 倒计时监听
     */
    abstract class CountdownListener {
        open fun countdown(time: Long) {}

        open fun countdownEnd() {}
    }

    companion object {

        /**
         * 倒计时
         *
         * @param time 倒计时时长（单位：秒）
         */
        fun countdown(time: Long, listener: CountdownListener): Disposable? {
            if (time < 0) {
                listener.countdownEnd()
                return null
            }
            return Observable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { aLong -> listener.countdown(time - aLong) }
                    .doOnComplete({ listener.countdownEnd() })
                    .subscribe()
        }
    }
}
