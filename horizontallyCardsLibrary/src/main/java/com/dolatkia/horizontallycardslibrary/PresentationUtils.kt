package com.dolatkia.horizontallycardslibrary

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

class PresentationUtils {

    companion object {

        private var DISPLAY_METRIC: DisplayMetrics? = null

        /* get screen dimen */
        private fun getDisplayMetrics(context: Context): DisplayMetrics? {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                return context.resources.displayMetrics
            } else if (DISPLAY_METRIC == null) {
                DISPLAY_METRIC = context.resources.displayMetrics
            }
            return DISPLAY_METRIC
        }

        fun getScreenWidthInPx(context: Context): Int {
            return getDisplayMetrics(context)!!.widthPixels
        }

        fun getScreenHeightInPx(context: Context): Int {
            return getDisplayMetrics(context)!!.heightPixels
        }

        fun convertDpToPixel(dp: Int, context: Context): Int {
            val px = dp * (getDisplayMetrics(context)!!.densityDpi / 160f)
            return px.roundToInt()
        }

        fun setStatusBarBackgroundColor(activity: Activity, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
                val decorView = window.decorView
                var flags = decorView.systemUiVisibility
                if (PresentationUtils.isColorLight(color)) {
                    if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == 0) {
                        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        decorView.systemUiVisibility = flags
                    }
                } else {
                    if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != 0) {
                        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                        decorView.systemUiVisibility = flags
                    }
                }
            }
        }

        private fun isColorLight(color: Int): Boolean {
            val darkness =
                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                    color
                )) / 255
            return darkness < 0.5
        }
    }
}