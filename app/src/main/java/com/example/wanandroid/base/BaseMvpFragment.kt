package com.example.wanandroid.base

import android.view.View
import com.example.wanandroid.ext.showToast

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/28 3:41 PM
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        this.mPresenter = null
    }


    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }
}