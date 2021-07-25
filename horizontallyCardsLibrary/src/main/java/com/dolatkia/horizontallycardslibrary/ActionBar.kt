package com.dolatkia.horizontallycardslibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import com.dolatkia.horizontallycardslibrary.databinding.ActionbarBinding

class ActionBar(context: Context, customView: View?) {

    private var translationYInAnimation: ObjectAnimator? = null
    private var translationYOutAnimation: ObjectAnimator? = null
    private var actionbarBinding: ActionbarBinding =
        ActionbarBinding.inflate(LayoutInflater.from(context))

    init {
        if (customView != null) {
            actionbarBinding.actionBarBg.addView(customView)
        }
        actionbarBinding.root.post {
            updateUI(0,1)
        }

        ViewCompat.setTranslationZ(
            actionbarBinding.closeImage,
            70f
        )
    }


    fun updateUI(
        offset: Int,
        titleTopOffset: Int
    ) {
        if (offset <= titleTopOffset) {
            if (translationYInAnimation == null) {
                actionbarBinding.actionBarBg.translationY = -actionbarBinding.root.height.toFloat()
            } else {
                startOutAnimation()
            }
        } else if (offset > titleTopOffset) {
            startInAnimation()
        } else {
            if (translationYInAnimation == null || !translationYInAnimation!!.isRunning) {
                actionbarBinding.actionBarBg.translationY = 0f
            }
        }
    }

    private fun startOutAnimation() {
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
        translationYOutAnimation?.duration = 300
        translationYOutAnimation?.start()
    }

    private fun startInAnimation() {
        if (translationYInAnimation != null && translationYInAnimation!!.isRunning) {
            return
        }
        if (translationYOutAnimation != null) {
            translationYOutAnimation!!.cancel()
        }
        translationYInAnimation =
            ObjectAnimator.ofFloat(actionbarBinding.actionBarBg, "translationY", 0f)
        translationYInAnimation?.startDelay = 0
        translationYInAnimation?.duration = 300
        translationYInAnimation?.start()
    }

    public fun getView(): View {
        return actionbarBinding.rootView;
    }
}