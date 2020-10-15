package com.example.wanandroid.ext

import com.example.wanandroid.app.App
import com.example.wanandroid.base.IModel
import com.example.wanandroid.base.IView
import com.example.wanandroid.http.exception.ErrorStatus
import com.example.wanandroid.http.exception.ExceptionHandle
import com.example.wanandroid.http.function.RetryWithDelay
import com.example.wanandroid.mvp.model.bean.BaseBean
import com.example.wanandroid.rx.SchedulerUtils
import com.example.wanandroid.utils.NetWorkUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 4:49 PM
 */
fun <T : BaseBean> Observable<T>.ss(model: IModel?, view: IView?, isShowLoading: Boolean = true, onSuccess: (T) -> Unit) {
    this.compose(SchedulerUtils.ioToMain())
            .retryWhen(RetryWithDelay())
            .subscribe(object : Observer<T> {
                override fun onComplete() {
                    view?.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    if (isShowLoading) view?.showLoading()
                    model?.addDisposable(d)
                    if (!NetWorkUtil.isNetworkAvailable(App.instance)) {
                        view?.showDefaultMsg("网络连接不可用，请检查网络设置！")
                        onComplete()
                    }
                }

                override fun onNext(t: T) {
                    when {
                        t.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                        t.errorCode == ErrorStatus.TOKEN_INVALID -> {
                            // Token过期，重新登录
                        }
                        else -> view?.showDefaultMsg(t.errorMsg)
                    }
                }

                override fun onError(e: Throwable) {
                    view?.hideLoading()
                    view?.showError(ExceptionHandle.handleException(e))
                }
            })
}

fun <T : BaseBean> Observable<T>.sss(view: IView?, isShowLoading: Boolean = true, onSuccess: (T) -> Unit, onError: ((T) -> Unit)? = null): Disposable {
    if (isShowLoading) view?.showLoading()
    return this.compose(SchedulerUtils.ioToMain())
            .retryWhen(RetryWithDelay())
            .subscribe({
                when {
                    it.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(it)
                    it.errorCode == ErrorStatus.TOKEN_INVALID -> {
                        // Token过期，重新登录
                    }
                    else -> {
                        if (onError != null) {
                            onError.invoke(it)
                        } else {
                            if (it.errorMsg.isNotEmpty()) {
                                view?.showDefaultMsg(it.errorMsg)
                            }
                        }
                    }
                }
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(it))
            })
}
