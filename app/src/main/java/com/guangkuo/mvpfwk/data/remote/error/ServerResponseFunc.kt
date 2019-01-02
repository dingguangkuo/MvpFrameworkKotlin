package com.guangkuo.mvpfwk.data.remote.error

import com.guangkuo.mvpfwk.data.bean.DataResponse
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * 拦截固定格式的公共数据类型DataResponse<T>, 判断里面的状态码
 */
class ServerResponseFunc<T> : Function<DataResponse<T>, T> {
    override fun apply(@NonNull tResult: DataResponse<T>): T? {
        // 对返回码进行判断
        if (tResult.result != 200) {
            // 如果服务器端有错误信息返回，
            // 那么抛出异常，让下面的方法去捕获异常做统一处理
            throw ServerException(tResult.result, tResult.msg)
        }
        return tResult.data// 服务器请求数据成功，返回里面的数据实体
    }
}