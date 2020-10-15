package com.example.wanandroid.base

import io.reactivex.disposables.Disposable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 10:22 AM
 */
interface IModel {
    fun addDisposable(disposable: Disposable?)

    fun onDetach()
}