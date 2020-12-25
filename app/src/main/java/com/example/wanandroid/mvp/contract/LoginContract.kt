package com.example.wanandroid.mvp.contract

import com.example.wanandroid.base.IModel
import com.example.wanandroid.base.IPresenter
import com.example.wanandroid.base.IView
import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/28 3:30 PM
 */
interface LoginContract {
    interface View: IView {
        fun loginSuccess(data: LoginData)

        fun loginFail()
    }

    interface Presenter: IPresenter<View> {
        fun loginWanAndroid(username: String, password: String)
    }

    interface Model: IModel {
        fun loginWanAndroid(username: String, password: String): Observable<HttpResult<LoginData>>
    }
}