package com.example.wanandroid.http.exception


/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 9:55 AM
 */
class ApiException : RuntimeException {
    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}