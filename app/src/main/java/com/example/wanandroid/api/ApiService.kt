package com.example.wanandroid.api

import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.mvp.model.bean.LoginData
import com.example.wanandroid.mvp.model.bean.UserInfoBody
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/10 11:54 AM
 */
interface ApiService {
    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

    /**
     * 获取个人积分，需要登录后访问
     * https://www.wanandroid.com/lg/coin/userinfo/json
     */
    @GET("/lg/coin/userinfo/json")
    fun getUserInfo(): Observable<HttpResult<UserInfoBody>>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun loginWanAndroid(@Field("username") username: String, @Field("password") password: String): Observable<HttpResult<LoginData>>

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    fun registerWanAndroid(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String): Observable<HttpResult<LoginData>>
}