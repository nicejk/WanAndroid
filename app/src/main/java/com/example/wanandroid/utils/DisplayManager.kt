package com.example.wanandroid.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/15 4:12 PM
 */
object DisplayManager {
    private var displayMetrics: DisplayMetrics? = null
    private var screenWidth: Int? = null
    private var screenHeight: Int? = null
    private var screenDpi: Int? = null

    fun init(context: Context) {
        displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
    }

    private val STANDROID_WIDTH = 1080
    private val STANDROID_HEIGHT = 1920

    fun getScreenWidth(): Int? {
        return screenWidth
    }

    fun getScreenHeight(): Int? {
        return screenHeight
    }
}