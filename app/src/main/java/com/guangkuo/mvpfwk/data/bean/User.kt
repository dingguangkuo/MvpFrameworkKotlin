package com.guangkuo.mvpfwk.data.bean

/**
 * User
 * Created by Guangkuo on 2018/12/29.
 */
class User {
    var id: Int = 0
    var username: String? = null
        get() = if (field == null) "" else field
}
