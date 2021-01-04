package com.example.wanandroid.mvp.contract

import com.example.wanandroid.base.IModel
import com.example.wanandroid.base.IPresenter
import com.example.wanandroid.base.IView
import com.example.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/25 10:44 AM
 */
interface CommonContract {
    interface View : IView {
        fun showCollectSuccess(success: Boolean)

        fun showCancelCollectSuccess(success: Boolean)
    }

    interface Presenter<in V : View> : IPresenter<V> {
        fun addCollectArticle(id: Int)

        fun cancelCollectArticle(id: Int)
    }

    interface Model : IModel {
        fun addCollectArticle(id: Int): Observable<HttpResult<Any>>

        fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>>
    }
}