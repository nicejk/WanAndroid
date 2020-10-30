package com.example.wanandroid.ext

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.wanandroid.widget.CustomToast

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/10/27 4:19 PM
 */
fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

fun Fragment.showToast(content: String) {
    CustomToast(this.activity?.applicationContext, content).show()
}