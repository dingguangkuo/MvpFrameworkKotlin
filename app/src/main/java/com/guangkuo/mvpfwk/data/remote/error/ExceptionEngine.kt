package com.guangkuo.mvpfwk.data.remote.error

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.JsonParseException
import com.guangkuo.mvpfwk.R
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

object ExceptionEngine {
    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {
            // HTTP错误（401/403/404/408/500/502等）
            LogUtils.e("HttpException code = " + e.code())
            ex = ApiException(e, ErrorCode.HTTP_ERROR, Utils.getApp().getString(R.string.error_network))
            return ex
        } else if (e is ServerException) {
            // 服务器返回的错误
            ex = ApiException(e, e.result, e.msg)
            return ex
        } else if (e is JsonParseException || e is JSONException || e is ParseException) {
            // 均视为解析错误
            ex = ApiException(e, ErrorCode.PARSE_ERROR, Utils.getApp().getString(R.string.error_parse))
            return ex
        } else if (e is ConnectException || e is SocketTimeoutException) {
            // 均视为网络错误
            ex = ApiException(e, ErrorCode.NETWORK_ERROR, Utils.getApp().getString(R.string.error_connection))
            return ex
        } else {
            // 未知错误
            e.printStackTrace()
            ex = ApiException(e, ErrorCode.UNKNOWN, Utils.getApp().getString(R.string.error_unknown))
            return ex
        }
    }
}