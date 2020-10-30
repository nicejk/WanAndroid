package com.example.wanandroid.base

import com.example.wanandroid.ext.showToast

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/27 4:17 PM
 */
abstract class BaseMvpActivity<in V: IView, P: IPresenter<V>> : BaseActivity(), IView {
    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
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