package com.guangkuo.mvpfwk.data.remote.error

/**
 * 定义服务器异常
 */
class ServerException(var result: Int, var msg: String) : RuntimeException()
