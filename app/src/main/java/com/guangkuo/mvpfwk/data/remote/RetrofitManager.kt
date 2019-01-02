package com.guangkuo.mvpfwk.data.remote

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.guangkuo.mvpfwk.BuildConfig
import com.guangkuo.mvpfwk.app.App
import com.guangkuo.mvpfwk.app.AppConfig
import com.guangkuo.mvpfwk.data.remote.interceptor.RewriteCacheControlInterceptor

import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 10L
    private const val WRITE_TIMEOUT = 10L

    @Volatile
    private var mOkHttpClient: OkHttpClient? = null
    // 云端响应头拦截器，用来配置缓存策略
    private val mRewriteCacheControlInterceptor = RewriteCacheControlInterceptor()

    /**
     * 获取OkHttpClient实例
     *
     * @return OkHttpClient实例
     */
    private val okHttpClient: OkHttpClient
        get() {
            if (mOkHttpClient == null) {
                synchronized(RetrofitManager::class.java) {
                    if (mOkHttpClient == null) {
                        val builder = OkHttpClient.Builder()
                                .cache(Cache(File(App.instance.cacheDir, "HttpCache"), (1024 * 1024 * 100).toLong()))
                                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                                .addInterceptor(mRewriteCacheControlInterceptor)
                                .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.instance)))
                        // Log信息拦截器
                        if (BuildConfig.DEBUG) {
                            val loggingInterceptor = HttpLoggingInterceptor()
                            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                            builder.addInterceptor(loggingInterceptor)
                        }
                        mOkHttpClient = builder.build()
                    }
                }
            }
            return mOkHttpClient!! // !! 表示：如果 mOkHttpClient 为空，就抛出 NullPointerException
        }

    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return </T>
     */
    fun <T> create(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.REQUEST_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(clazz)
    }
}
