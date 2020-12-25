package com.example.wanandroid.mvp.presenter

import com.example.wanandroid.base.BasePresenter
import com.example.wanandroid.ext.ss
import com.example.wanandroid.mvp.contract.RegisterContract
import com.example.wanandroid.mvp.model.RegisterModel

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/28 6:15 PM
 */
class RegisterPresenter : BasePresenter<RegisterContract.Model, RegisterContract.View>(), RegisterContract.Presenter {

    override fun createModel(): RegisterContract.Model? = RegisterModel()

    override fun registerWanAndroid(username: String, password: String, repassword: String) {
        mModel?.registerWanAndroid(username, password, repassword)?.ss(mModel, mView) {
            mView?.apply {
                if (it.errorCode != 0) {
                    registerFail()
                } else {
                    registerSuccess(it.data)
                }
            }
        }
    }
}