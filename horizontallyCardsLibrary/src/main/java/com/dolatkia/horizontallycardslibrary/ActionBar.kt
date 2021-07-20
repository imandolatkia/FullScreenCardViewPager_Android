package com.dolatkia.horizontallycardslibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.FrameLayout

class ActionBar(context: Context, customView: View?) : FrameLayout(context) {

    private var translationYInAnimation: ObjectAnimator? = null
    private var translationYOutAnimation: ObjectAnimator? = null

//
//    init {
//        if (customView != null) {
//            addView(customView)
//        }
//    }
//
//
//    fun updateUI(
//        offset: Int,
//        isFirstItem: Boolean,
//        titleTopOffset: Int,
//        buyBtnTopOffset: Int
//    ) {
//        if (offset <= titleTopOffset && isFirstItem) {
//            if (translationYInAnimation == null) {
//                background.setTranslationY(-height.toFloat())
//            } else {
//                startOutAnimation()
//            }
//        } else if (offset > titleTopOffset && isFirstItem) {
//            startInAnimation()
//        } else {
//            if (translationYInAnimation == null || !translationYInAnimation.isRunning()) {
//                background.setTranslationY(0f)
//            }
//        }
//    }
//
//    private fun startOutAnimation() {
//        if (translationYOutAnimation != null && translationYOutAnimation!!.isRunning()) {
//            return
//        }
//        translationYInAnimation?.cancel()
//        translationYOutAnimation =
//            ObjectAnimator.ofFloat(background, "translationY", -height.toFloat())
//        translationYOutAnimation?.duration = 300
//        translationYOutAnimation?.start()
//    }
//
//    private fun startInAnimation() {
//        if (translationYInAnimation != null && translationYInAnimation!!.isRunning) {
//            return
//        }
//        if (translationYOutAnimation != null) {
//            translationYOutAnimation!!.cancel()
//        }
//        translationYInAnimation = ObjectAnimator.ofFloat(background, "translationY", 0f)
//        translationYInAnimation?.duration = 300
//        translationYInAnimation?.start()
//    }
}