package com.example.wanandroid.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

/**
 *
 * @Description：
 * @Author: haishan
 * @CreateDate: 2020/11/28 5:30 PM
 */
object DialogUtil {
    /**
     * 获取一个Dialog
     * @param context Context
     * @return
     */
    fun getDialog(context: Context): AlertDialog.Builder {
        return AlertDialog.Builder(context)
    }


    /**
     * 获取一个耗时的对话框 ProgressDialog
     *
     * @param context
     * @param message
     * @return
     */
    fun getWaitDialog(context: Context, message: String?): ProgressDialog {
        val waitDialog = ProgressDialog(context)
        waitDialog.setMessage(message)
        return waitDialog
    }

    fun getConfirmDialog(context: Context, message: String, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setMessage(message)
        builder.setPositiveButton("确定", onClickListener)
        builder.setNegativeButton("取消", null)
        return builder
    }
}