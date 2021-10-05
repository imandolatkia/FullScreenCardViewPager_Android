package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
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
    }
}