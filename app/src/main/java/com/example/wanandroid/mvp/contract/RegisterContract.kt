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
 * @CreateDate: 2020/11/28 6:11 PM
 */
interface RegisterContract {
    interface View: IView {
        fun registerSuccess(data: LoginData)

        fun registerFail()
    }

    interface Presenter: IPresenter<View> {
        fun registerWanAndroid(username: String, password: String, repassword: String)
    }

    interface Model: IModel {
        fun registerWanAndroid(username: String, password: String, repassword: String): Observable<HttpResult<LoginData>>
    }
}