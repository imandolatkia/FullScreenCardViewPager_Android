package com.dolatkia.horizontallycardslibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import com.dolatkia.horizontallycardslibrary.databinding.ActionbarBinding

class CardActionBar(context: Context, var customView: View) {

    private var translationYInAnimation: ObjectAnimator? = null
    private var translationYOutAnimation: ObjectAnimator? = null
    private var actionbarBinding: ActionbarBinding =
        ActionbarBinding.inflate(LayoutInflater.from(context))
    private var animDuration: Long = 300

    init {
        // invisible actionbar at first
        actionbarBinding.actionBarBg.visibility = View.INVISIBLE

        // add custom actionbar view
        actionbarBinding.actionBarBg.addView(customView)

        // bring actionbar to front
        ViewCompat.setTranslationZ(
            actionbarBinding.closeImage,
            70f
        )
        ViewCompat.setTranslationZ(
            actionbarBinding.rootView,
            60f
        )

        // set negative Y translate for actionbar
        actionbarBinding.actionBarBg.translationY = -actionbarBinding.root.height.toFloat()
    }


    fun updateUI(
        offset: Int,
        titleTopOffset: Int
    ): Boolean {
        //update UI according to top offset
        return if (offset <= titleTopOffset) {
            startOutAnimation()
            false
        } else {
            startInAnimation()
            true
        }
    }

    private fun startOutAnimation() {
        if (actionbarBinding.actionBarBg.translationY == -actionbarBinding.root.height.toFloat()) {
            return
        }

        if (translationYOutAnimation != null && translationYOutAnimation!!.isRunning) {
            return
        }
        translationYInAnimation?.cancel()
        translationYOutAnimation =
            ObjectAnimator.ofFloat(
                actionbarBinding.actionBarBg,
                "translationY",
                -actionbarBinding.root.height.toFloat()
            )
        translationYOutAnimation?.startDelay = 0
        translationYOutAnimation?.duration = animDuration
        translationYOutAnimation?.start()
    }

    private fun startInAnimation() {
        actionbarBinding.actionBarBg.visibility = View.VISIBLE

        if (actionbarBinding.actionBarBg.translationY == 0f) {
            return
        }

        if (translationYInAnimation != null && translationYInAnimation!!.isRunning) {
            return
        }
        if (translationYOutAnimation != null) {
            translationYOutAnimation!!.cancel()
        }
        translationYInAnimation =
            ObjectAnimator.ofFloat(actionbarBinding.actionBarBg, "translationY", 0f)
        translationYInAnimation?.startDelay = 0
        translationYInAnimation?.duration = animDuration
        translationYInAnimation?.start()
    }

    fun setCloseImageRes(closeResId: Int) {
        actionbarBinding.closeImage.setImageResource(closeResId)
    }

    fun setCloseImageColor(color: Int) {
        actionbarBinding.closeImage.setColorFilter(color)
    }

    fun setCLoseClickListener(click: View.OnClickListener) {
        actionbarBinding.closeImage.setOnClickListener(click)
    }

    fun getView(): View {
        return actionbarBinding.rootView
    }
}