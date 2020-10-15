package com.example.wanandroid.app

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/10 2:30 PM
 */
class App : Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }
}