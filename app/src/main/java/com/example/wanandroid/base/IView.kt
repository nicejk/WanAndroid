package com.example.wanandroid.base

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 10:23 AM
 */
interface IView {
    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 使用默认的样式显示信息: CustomToast
     */
    fun showDefaultMsg(msg: String)

    /**
     * 显示信息
     */
    fun showMsg(msg: String)

    /**
     * 显示错误信息
     */
    fun showError(errorMsg: String)

}