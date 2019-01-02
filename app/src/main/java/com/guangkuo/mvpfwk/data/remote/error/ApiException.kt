package com.guangkuo.mvpfwk.data.remote.error

/**
 * 自定义 ApiException
 */
class ApiException(throwable: Throwable, result: Int) : Exception(throwable) {
    var result: Int = result
    var msg: String? = null
        get() = if (field == null) "" else field

    constructor(throwable: Throwable, result: Int, msg: String?) : this(throwable, result) {
        this.result = result
        this.msg = msg
    }
}
