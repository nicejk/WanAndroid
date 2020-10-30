package com.example.wanandroid.base

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.wanandroid.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/28 3:51 PM
 */
abstract class BaseMvpListFragment<in V: IView, P: IPresenter<V>> : BaseMvpFragment<V, P>() {
    /**
     * 每页数据的个数
     */
    protected var pageSize = 20

    /**
     * 是否是下拉刷新
     */
    private var isRefresh = false

    protected val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    protected val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    protected val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        onRefreshList()
    }

    protected val onLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        onLoadMoreList()
    }

    /**
     * 下拉刷新
     */
    abstract fun onRefreshList()

    /**
     * 上拉加载更多
     */
    abstract fun onLoadMoreList()

    override fun initView(view: View) {
        super.initView(view)

        mLayoutStatusView = multiple_status_view
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

        recyclerView.run {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let {
                addItemDecoration(it)
            }
        }
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
    }
}