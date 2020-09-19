package com.zaythel.consumer.ui.utils

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat

fun Window.applyTransparentStatusBar() = this.apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        statusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.getColor(context, android.R.color.white)
        } else {
            ContextCompat.getColor(context, android.R.color.white)
        }
    }

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN -> decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_VISIBLE
    }
}