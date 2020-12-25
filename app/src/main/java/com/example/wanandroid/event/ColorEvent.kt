package com.example.wanandroid.event

import com.example.wanandroid.utils.SettingUtil

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/27 2:27 PM
 */
class ColorEvent(var isRefresh: Boolean, var color: Int = SettingUtil.getColor())