package com.example.wanandroid.mvp.presenter

import com.example.wanandroid.base.BasePresenter
import com.example.wanandroid.ext.ss
import com.example.wanandroid.mvp.contract.LoginContract
import com.example.wanandroid.mvp.model.LoginModel

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/28 3:36 PM
 */
class LoginPresetner : BasePresenter<LoginContract.Model, LoginContract.View>(), LoginContract.Presenter {
    override fun createModel(): LoginContract.Model? = LoginModel()

    override fun loginWanAndroid(username: String, password: String) {
        mModel?.loginWanAndroid(username, password)?.ss(mModel, mView) {
            mView?.loginSuccess(it.data)
        }
    }
}