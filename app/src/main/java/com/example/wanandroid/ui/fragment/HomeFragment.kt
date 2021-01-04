package com.example.wanandroid.ui.fragment

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.wanandroid.R
import com.example.wanandroid.adapter.HomeAdapter
import com.example.wanandroid.base.BaseMvpFragment
import com.example.wanandroid.ext.showToast
import com.example.wanandroid.mvp.contract.HomeContract
import com.example.wanandroid.mvp.model.bean.Article
import com.example.wanandroid.mvp.model.bean.ArticleResponseBody
import com.example.wanandroid.mvp.model.bean.Banner
import com.example.wanandroid.mvp.presenter.HomePresenter
import com.example.wanandroid.utils.ImageLoader
import com.example.wanandroid.widget.SpaceItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import kotlinx.android.synthetic.main.item_home_banner.view.*

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 10:37 AM
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {
    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    /**
     * banner datas
     */
    private lateinit var bannerDatas: ArrayList<Banner>

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { _, imageView, feedImageUrl, _ ->
            ImageLoader.load(activity, feedImageUrl, imageView)
        }
    }

    /**
     * datas
     */
    private val datas: MutableList<Article> = mutableListOf()

    /**
     * Home Adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, datas)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * banner view
     */
    private var bannerView: View? = null

    // 是否刷新
    private var isRefresh = true

    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view

        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

        recyclerView.run {
            adapter = homeAdapter
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)
        bannerView?.banner?.run {
            setDelegate(bannerDelegate)
        }

        homeAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@HomeFragment.onItemCLickListener
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            addHeaderView(bannerView)
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestHomeData()
    }

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun scrollToTop() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    /**
     * 设置banner数据
     */
    override fun setBanner(banners: List<Banner>) {
        bannerDatas = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners).subscribe {
            bannerFeedList.add(it.imagePath)
            bannerTitleList.add(it.title)
        }
        bannerView?.banner?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }
    }

    /**
     * 设置文章列表数据
     */
    override fun setArticles(articles: ArticleResponseBody) {
        articles.datas.let {
            homeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }

                val size = it.size
                if (size < articles.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }

        if (homeAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    /**
     * 收藏成功
     */
    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.collect_success))
        }
    }

    /**
     * 取消收藏成功
     */
    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.cancel_collect_success))
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            homeAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        homeAdapter.run {
            if (isRefresh) {
                setEnableLoadMore(true)
            } else {
                loadMoreFail()
            }
        }
    }

    /**
     * 下拉刷新列表
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        homeAdapter.setEnableLoadMore(false)
        mPresenter?.requestHomeData()
    }

    /**
     * 上拉加载更多
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = homeAdapter.data.size / 20
        mPresenter?.requestArticles(page)
    }

    /**
     * banner点击监听
     */
    private val bannerDelegate = BGABanner.Delegate<ImageView, String> { _, _, _, position ->
        if (bannerDatas.size > 0) {
            val data = bannerDatas[position]
            // TODO: 2020/12/29 跳转到详情
        }
    }

    /**
     * 点击条目监听
     */
    private val onItemCLickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size > 0) {
            val data = datas[position]
            // TODO: 2020/12/29 跳转到详情
        }
    }

    /**
     * 点击子条目监听
     */
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, _, position ->

    }
}