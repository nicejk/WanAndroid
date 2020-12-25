package com.example.wanandroid.mvp.model

import com.example.wanandroid.base.BaseModel
import com.example.wanandroid.http.RetrofitHelper
import com.example.wanandroid.mvp.contract.MainContract
import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.mvp.model.bean.UserInfoBody
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 10:56 AM
 */
class MainModel : BaseModel(), MainContract.Model {
    override fun logout(): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.logout()
    }

    override fun getUserInfo(): Observable<HttpResult<UserInfoBody>> {
        return RetrofitHelper.service.getUserInfo()
    }
}