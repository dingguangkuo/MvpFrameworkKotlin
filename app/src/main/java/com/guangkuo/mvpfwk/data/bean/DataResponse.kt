package com.guangkuo.mvpfwk.data.bean

/**
 * 响应数据
 *
 * Created by Guangkuo on 2018/12/25.
 */
class DataResponse<T> {
    var result: Int = 200

    var msg: String? = null
        get() = if (field == null) "" else field

    var data: T? = null
}
