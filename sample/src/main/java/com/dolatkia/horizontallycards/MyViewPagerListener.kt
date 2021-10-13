package com.dolatkia.horizontallycards

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import androidx.core.content.ContextCompat
import com.dolatkia.horizontallycardslibrary.CardChangeListener
import com.dolatkia.horizontallycardslibrary.PresentationUtils

class MyViewPagerListener(var activity: Activity) : CardChangeListener {
    private var colorAnimation: ValueAnimator? = null

    override fun onExpandChanged(isExpanded: Boolean) {
        if (isExpanded) {
            changeStatusParColor(ContextCompat.getColor(activity, R.color.cards_background), ContextCompat.getColor(activity, R.color.white))
        } else {
            changeStatusParColor(ContextCompat.getColor(activity, R.color.white),ContextCompat.getColor(activity, R.color.cards_background))
        }
    }

    override fun onScroll(offset: Int) {
    }

    private fun changeStatusParColor(colorFrom: Int, colorTo: Int) {
        if (colorAnimation != null && colorAnimation?.isRunning == true) {
            colorAnimation?.cancel()
        }
        colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)

        colorAnimation?.addUpdateListener { animator ->
            val color = animator.animatedValue as Int
            PresentationUtils.setStatusBarBackgroundColor(activity, color)
        }
        colorAnimation?.start()
    }
}