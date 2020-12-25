package com.example.wanandroid.utils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/12 3:31 PM
 */
object AnimatorUtil {
    private val FAST_OUT_SLOW_IN_INTERPOLATOR: LinearOutSlowInInterpolator by lazy {
        LinearOutSlowInInterpolator()
    }

    /**
     * 显示view
     */
    fun translateShow(view: View, listener: ViewPropertyAnimatorListener) {
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
            .translationY(0f)
            .setDuration(400)
            .setListener(listener)
            .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
            .start()
    }

    /**
     * 隐藏view
     */
    fun translateHide(view: View, listener: ViewPropertyAnimatorListener) {
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
            .translationY(350f)
            .setDuration(400)
            .setListener(listener)
            .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
            .start()
    }
}