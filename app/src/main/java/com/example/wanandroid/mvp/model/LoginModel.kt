package com.example.wanandroid.mvp.model

import com.example.wanandroid.base.BaseModel
import com.example.wanandroid.http.RetrofitHelper
import com.example.wanandroid.mvp.contract.LoginContract
import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/28 3:32 PM
 */
class LoginModel : BaseModel(), LoginContract.Model{
    override fun loginWanAndroid(username: String, password: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.loginWanAndroid(username, password)
    }
}