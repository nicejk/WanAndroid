package com.example.wanandroid.mvp.contract

import com.example.wanandroid.base.IModel
import com.example.wanandroid.base.IPresenter
import com.example.wanandroid.base.IView
import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.mvp.model.bean.UserInfoBody
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 10:45 AM
 */
interface MainContract {
    interface View : IView {
        fun showLogoutSuccess(success: Boolean)

        fun showUserInfo(bean: UserInfoBody)
    }

    interface Presenter : IPresenter<View> {
        fun logout()

        fun getUserInfo()
    }

    interface Model : IModel {
        fun logout(): Observable<HttpResult<Any>>

        fun getUserInfo(): Observable<HttpResult<UserInfoBody>>
    }
}