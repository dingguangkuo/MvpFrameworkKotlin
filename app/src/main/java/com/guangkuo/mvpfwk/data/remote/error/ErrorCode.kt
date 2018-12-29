package com.guangkuo.mvpfwk.data.remote.error

internal object ErrorCode {
    /**
     * 未知错误
     */
    val UNKNOWN: Int = 1000
    /**
     * 解析错误
     */
    val PARSE_ERROR: Int = 1001
    /**
     * 网络错误
     */
    val NETWORK_ERROR: Int = 1002
    /**
     * 协议出错
     */
    val HTTP_ERROR: Int = 1003
}