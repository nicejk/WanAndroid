package com.example.wanandroid.mvp.model.bean

import com.squareup.moshi.Json

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 10:47 AM
 */
data class HttpResult<T>(@Json(name = "data") val data: T) : BaseBean()

data class UserInfoBody(
    @Json(name = "coinCount") val coinCount: Int,
    @Json(name = "rank") val rank: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "username") val username: String
)

data class LoginData(
    @Json(name = "chapterTops") val chapterTops: MutableList<String>,
    @Json(name = "collectIds") val collectIds: MutableList<String>,
    @Json(name =  "email") val email: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "password") val password: String,
    @Json(name = "token") val token: String,
    @Json(name = "type") val type: Int,
    @Json(name = "username") val username: String
)