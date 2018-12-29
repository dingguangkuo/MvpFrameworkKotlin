package com.guangkuo.mvpfwk.data.remote.api

import com.guangkuo.mvpfwk.data.bean.DataResponse
import com.guangkuo.mvpfwk.data.bean.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ApiService
 * Created by Guangkuo on 2018/12/29.
 */
interface ApiService {
    /**
     * 登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<DataResponse<User>>

    /**
     * 注册用户的方法
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String): Observable<DataResponse<*>>
}
