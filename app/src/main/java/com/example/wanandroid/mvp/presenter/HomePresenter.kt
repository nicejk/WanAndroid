package com.example.wanandroid.mvp.presenter

import com.example.wanandroid.ext.ss
import com.example.wanandroid.mvp.contract.HomeContract
import com.example.wanandroid.mvp.model.HomeModel
import com.example.wanandroid.mvp.model.bean.Article
import com.example.wanandroid.mvp.model.bean.ArticleResponseBody
import com.example.wanandroid.mvp.model.bean.HttpResult
import com.example.wanandroid.utils.SettingUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/25 11:17 AM
 */
class HomePresenter : CommonPresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun requestBanner() {
        mModel?.requestBanner()?.ss(mModel, mView, false) {
            mView?.setBanner(it.data)
        }
    }

    override fun requestArticles(num: Int) {
        mModel?.requestArticles(num)?.ss(mModel, mView, num == 0) {
            mView?.setArticles(it.data)
        }
    }

    override fun requestHomeData() {
        requestBanner()

        val observable = if (SettingUtil.getIsShowTopArticle()) {
            mModel?.requestArticles(0)
        } else {
            Observable.zip(mModel?.requestTopArticles(), mModel?.requestArticles(0),
                BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleResponseBody>,
                        HttpResult<ArticleResponseBody>> { t1, t2 ->
                    t1.data.forEach {
                        // 置顶数据中没有标识，手动添加一个标识
                        it.top = "1"
                    }
                    t2.data.datas.addAll(0, t1.data)
                    t2
                })
        }

        observable?.ss(mModel, mView, false) {
            mView?.setArticles(it.data)
        }
    }
}