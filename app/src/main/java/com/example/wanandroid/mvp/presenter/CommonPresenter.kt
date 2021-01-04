package com.example.wanandroid.mvp.presenter

import com.example.wanandroid.base.BasePresenter
import com.example.wanandroid.ext.ss
import com.example.wanandroid.mvp.contract.CommonContract

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/25 10:48 AM
 */
open class CommonPresenter<M: CommonContract.Model, V: CommonContract.View> : BasePresenter<M, V>(), CommonContract.Presenter<V> {
    override fun addCollectArticle(id: Int) {
        mModel?.addCollectArticle(id)?.ss(mModel, mView) {
            mView?.showCollectSuccess(true)
        }
    }

    override fun cancelCollectArticle(id: Int) {
        mModel?.cancelCollectArticle(id)?.ss(mModel, mView) {
            mView?.showCancelCollectSuccess(true)
        }
    }
}