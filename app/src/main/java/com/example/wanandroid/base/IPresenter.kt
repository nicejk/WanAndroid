package com.example.wanandroid.base

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 10:26 AM
 */
interface IPresenter<in V : IView> {
    /**
     * 绑定View
     */
    fun attachView(mView: V)

    /**
     * 解绑View
     */
    fun detachView()

}