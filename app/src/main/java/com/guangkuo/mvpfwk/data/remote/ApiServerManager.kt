package com.guangkuo.mvpfwk.data.remote

import com.guangkuo.mvpfwk.base.BaseContract
import com.guangkuo.mvpfwk.data.bean.DataResponse
import com.guangkuo.mvpfwk.data.bean.User
import com.guangkuo.mvpfwk.data.remote.api.ApiService
import com.guangkuo.mvpfwk.data.remote.error.HttpResponseFunc
import com.guangkuo.mvpfwk.data.remote.error.ServerResponseFunc
import com.guangkuo.mvpfwk.data.remote.observer.MyObserver
import com.guangkuo.mvpfwk.data.remote.observer.RetryWithDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ApiServerManager
 * Created by Guangkuo on 2019/1/2.
 */
class ApiServerManager {
    /**
     * Observable 管理
     *
     * @param observable 被观察者
     * @param myObserver 观察者
     * @param <T>        泛型参数
    </T> */
    private fun <T, V : BaseContract.BaseView> observableManage(observable: Observable<DataResponse<T>>,
                                                                myObserver: MyObserver<T, V>) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(RetryWithDelay(RETRY_COUNT, RETRY_DELAY))
                .map(ServerResponseFunc())
                .onErrorResumeNext(HttpResponseFunc<T>())
                .subscribe(myObserver)
    }

    /**
     * 注册
     */
    fun <V : BaseContract.BaseView> login(username: String, password: String, myObserver: MyObserver<User, V>) {
        this.observableManage(mApiService.login(username, password), myObserver)
    }

    /**
     * 伴生对象
     */
    companion object {
        private const val RETRY_COUNT = 2// 失败重试次数
        private const val RETRY_DELAY = 3000// 失败重试延时（ms）
        private var mApiServerManager: ApiServerManager? = null
        private var mApiService: ApiService = RetrofitManager.create(ApiService::class.java)
        /**
         * 单例
         *
         * @return ApiServerManager 实例
         */
        fun getInstance(): ApiServerManager {
            if (mApiServerManager == null) {
                synchronized(ApiServerManager::class.java) {
                    if (mApiServerManager == null) {
                        mApiServerManager = ApiServerManager()
                    }
                }
            }
            return mApiServerManager!! // !! 如果为空，抛异常
        }
    }
}