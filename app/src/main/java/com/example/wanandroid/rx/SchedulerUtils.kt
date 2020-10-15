package com.example.wanandroid.rx

import com.example.wanandroid.rx.scheduler.IoMainScheduler

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/14 9:54 AM
 */
object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}