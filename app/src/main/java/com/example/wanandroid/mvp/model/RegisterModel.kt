package com.example.wanandroid.mvp.model

import com.example.wanandroid.base.BaseModel
import com.example.wanandroid.http.RetrofitHelper
import com.example.wanandroid.mvp.contract.RegisterContract
import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/28 6:13 PM
 */
class RegisterModel : BaseModel(), RegisterContract.Model {
    override fun registerWanAndroid(username: String, password: String, repassword: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.registerWanAndroid(username, password, repassword)
    }
}