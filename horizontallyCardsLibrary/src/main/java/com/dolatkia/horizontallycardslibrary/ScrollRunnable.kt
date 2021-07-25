package com.dolatkia.horizontallycardslibrary

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ScrollRunnable(private val viewPager2: ViewPager2) {
    fun onScroll(offset: Int) {
        val pageOffset: Int = PresentationUtils.convertDpToPixel(25, viewPager2.context)
        viewPager2.setPageTransformer { view: View, position: Float ->
            if (position >= -1 && position <= 1) { // [-1,1]
                val myOffset = position * -pageOffset
                view.translationX = myOffset + offset
            }
        }
    }
}