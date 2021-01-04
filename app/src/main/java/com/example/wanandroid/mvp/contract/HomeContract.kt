package com.example.wanandroid.mvp.contract

import com.example.wanandroid.mvp.model.bean.Article
import com.example.wanandroid.mvp.model.bean.ArticleResponseBody
import com.example.wanandroid.mvp.model.bean.Banner
import com.example.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/25 10:40 AM
 */
interface HomeContract {
    interface View : CommonContract.View {
        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticles(articles: ArticleResponseBody)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun requestBanner()

        fun requestHomeData()

        fun requestArticles(num: Int)
    }

    interface Model : CommonContract.Model {
        fun requestBanner(): Observable<HttpResult<List<Banner>>>

        fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>>

        fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>>
    }
}