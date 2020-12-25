package com.example.wanandroid.mvp.presenter

import com.example.wanandroid.base.BasePresenter
import com.example.wanandroid.ext.ss
import com.example.wanandroid.ext.sss
import com.example.wanandroid.mvp.contract.MainContract
import com.example.wanandroid.mvp.model.MainModel

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 11:02 AM
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {
    override fun createModel(): MainContract.Model? = MainModel()

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView) {
            mView?.showLogoutSuccess(success = true)
        }
    }

    override fun getUserInfo() {
        mModel?.getUserInfo()?.sss(mView, false, {
            mView?.showUserInfo(it.data)
        }, {})
    }
}