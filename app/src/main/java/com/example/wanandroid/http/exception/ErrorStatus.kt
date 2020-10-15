package com.example.wanandroid.http.exception

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 9:59 AM
 */
object ErrorStatus {
    /**
     * 响应成功
     */
    const val SUCCESS = 0

    /**
     * Token 过期
     */
    const val TOKEN_INVALID = 401

    /**
     * 未知错误
     */
    const val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    const val SERVER_ERROR = 1003

    /**
     * 网络连接超时
     */
    const val NETWORK_ERROR = 1004
}