package com.guangkuo.mvpfwk.data.remote.error

/**
 * 自定义 ApiException
 */
class ApiException(throwable: Throwable, result: Int, msg: String) : Exception(throwable) {
    var result: Int = result
    var msg: String = msg
}
