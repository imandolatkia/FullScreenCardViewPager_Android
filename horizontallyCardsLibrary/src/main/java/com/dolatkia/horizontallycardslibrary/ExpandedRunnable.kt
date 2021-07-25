package com.dolatkia.horizontallycardslibrary

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ExpandedRunnable(private val viewPager2: ViewPager2) {
    fun onExpandChanged(isExpanded: Boolean) {
        viewPager2.isUserInputEnabled = isExpanded
    }

    fun onScroll(offset: Int) {
        val pageOffset: Int = PresentationUtils.convertDpToPixel(25, viewPager2.context)
        viewPager2.setPageTransformer { view: View, position: Float ->
            var myOffset = 0f;
            if (position >= -1 && position <= 1) { // [-1,1]
                myOffset = position * -pageOffset
            }

            if (position > 0) {
                myOffset += offset / 4
            } else if (position < 0) {
                myOffset -= offset / 4
            }
            view.translationX = myOffset
        }
    }
}