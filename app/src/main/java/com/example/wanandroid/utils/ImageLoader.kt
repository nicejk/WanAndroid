package com.example.wanandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.wanandroid.R
import com.example.wanandroid.app.App

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/12/29 2:44 PM
 */
object ImageLoader {
    private val isLoadImage = !SettingUtil.getIsNoPhotoMode() || NetWorkUtil.isWifi(App.context)

    fun load(context: Context?, url: String?, iv: ImageView?) {
        if (isLoadImage) {
            iv?.apply {
                context?.let { Glide.with(it).clear(iv) }
                val options = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.bg_placeholder)
                context?.let {
                    Glide.with(it)
                        .load(url)
                        .transition(DrawableTransitionOptions().crossFade())
                        .apply(options)
                        .into(iv)
                }

            }
        }
    }
}