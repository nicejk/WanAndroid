package com.example.wanandroid.mvp.model

import com.example.wanandroid.http.RetrofitHelper
import com.example.wanandroid.mvp.contract.HomeContract
import com.example.wanandroid.mvp.model.bean.Article
import com.example.wanandroid.mvp.model.bean.ArticleResponseBody
import com.example.wanandroid.mvp.model.bean.Banner
import com.example.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/25 11:14 AM
 */
class HomeModel : CommonModel(), HomeContract.Model {
    override fun requestBanner(): Observable<HttpResult<List<Banner>>> {
        return RetrofitHelper.service.getBanners()
    }

    override fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return RetrofitHelper.service.getTopArticles()
    }

    override fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.getArticles(num)
    }
}