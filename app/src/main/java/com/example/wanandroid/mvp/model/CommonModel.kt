package com.example.wanandroid.mvp.model

import com.example.wanandroid.base.BaseModel
import com.example.wanandroid.http.RetrofitHelper
import com.example.wanandroid.mvp.contract.CommonContract
import com.example.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/25 10:48 AM
 */
open class CommonModel : BaseModel(), CommonContract.Model {
    override fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.addCollectArticle(id)
    }

    override fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.cancelCollectArticle(id)
    }
}