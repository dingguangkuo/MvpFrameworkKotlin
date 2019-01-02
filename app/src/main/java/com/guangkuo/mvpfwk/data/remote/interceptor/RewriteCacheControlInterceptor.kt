package com.guangkuo.mvpfwk.data.remote.interceptor

import com.guangkuo.mvpfwk.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 云端响应头拦截器，用来配置缓存策略
 *
 * Created by Guangkuo on 2018/12/25.
 */
class RewriteCacheControlInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()// 获得上一个请求
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val originalResponse = chain.proceed(request)
        return if (NetworkUtils.isConnected()) {
            // 有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
            val cacheControl = request.cacheControl().toString()
            originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build()
        } else {
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_CONTROL_CACHE")
                    .removeHeader("Pragma")
                    .build()
        }
    }

    companion object {
        // 设缓存有效期为1天
        private const val CACHE_STALE_SEC = (60 * 60 * 24).toLong()
        // 查询缓存的Cache-Control设置，为only-if-cached时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
        private const val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_SEC"
        // 查询网络的Cache-Control设置
        // (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
        private const val CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=10"
        // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
        private const val AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
    }
}
