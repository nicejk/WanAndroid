package com.example.wanandroid.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wanandroid.constant.Constant
import com.example.wanandroid.event.NetworkChangeEvent
import com.example.wanandroid.utils.NetWorkUtil
import com.example.wanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/16 10:22 AM
 */
class NetworkChangeReceiver : BroadcastReceiver() {
    private var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = NetWorkUtil.isNetworkAvailable(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }
}