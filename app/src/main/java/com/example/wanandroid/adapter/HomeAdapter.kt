package com.example.wanandroid.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.mvp.model.bean.Article
import com.example.wanandroid.utils.ImageLoader

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/28 3:36 PM
 */
class HomeAdapter(private val context: Context?, datas: MutableList<Article>) : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_home_list, datas) {
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        item ?: return
        helper ?: return
        val authorStr = if (item.author.isNotEmpty()) item.author else item.shareUser
        helper.setText(R.id.tv_article_title, Html.fromHtml(item.title))
                .setText(R.id.tv_article_author, authorStr)
                .setText(R.id.tv_article_date, item.niceDate)
                .setImageResource(R.id.iv_like, if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not)
                .addOnClickListener(R.id.iv_like)

        val chapterName = when {
            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() -> "${item.superChapterName} / ${item.chapterName}"
            item.superChapterName.isNotEmpty() -> item.superChapterName
            item.chapterName.isNotEmpty() -> item.chapterName
            else -> ""
        }
        helper.setText(R.id.tv_article_chapterName, chapterName)
        if (item.envelopePic.isNotEmpty()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.VISIBLE
            ImageLoader.load(context, item.envelopePic, helper.getView(R.id.iv_article_thumbnail))
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.GONE
        }

        // 最新
        val tvRefresh = helper.getView<TextView>(R.id.tv_article_fresh)
        if (item.fresh) {
            tvRefresh.visibility = View.VISIBLE
        } else {
            tvRefresh.visibility = View.GONE
        }

        // 置顶
        val tvTop = helper.getView<TextView>(R.id.tv_article_top)
        if (item.top == "1") {
            tvTop.visibility = View.VISIBLE
        } else {
            tvTop.visibility = View.GONE
        }

        // 文章
        val tvArticleTag = helper.getView<TextView>(R.id.tv_article_tag)
        if (item.tags.size > 0) {
            tvArticleTag.visibility = View.VISIBLE
            tvArticleTag.text = item.tags[0].name
        } else {
            tvArticleTag.visibility = View.GONE
        }
    }
}