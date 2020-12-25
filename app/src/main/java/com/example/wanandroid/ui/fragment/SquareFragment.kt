package com.example.wanandroid.ui.fragment

import android.view.View
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseFragment

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/13 10:38 AM
 */
class SquareFragment : BaseFragment(){
    companion object {
        fun getInstance(): SquareFragment = SquareFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_square

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}