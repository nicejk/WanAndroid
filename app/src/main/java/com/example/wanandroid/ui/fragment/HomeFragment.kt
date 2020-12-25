package com.example.wanandroid.ui.fragment

import android.view.View
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseFragment

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 10:37 AM
 */
class HomeFragment : BaseFragment() {


    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout
    override fun initView(view: View) {

    }


    override fun lazyLoad() {
    }
}