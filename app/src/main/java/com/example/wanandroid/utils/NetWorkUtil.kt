package com.example.wanandroid.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/10 4:59 PM
 */
class NetWorkUtil {
    companion object {
        /**
         * check NetworkAvailable
         */
        @JvmStatic
        fun isNetworkAvailable(context: Context?): Boolean {
            val manager = context?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }

        @JvmStatic
        fun isWifi(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }
}