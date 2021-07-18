package com.dolatkia.horizontallycardslibrary

import android.util.DisplayMetrics
import android.view.View
import android.widget.AbsListView
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily


class CardViewHolder(itemView: View, var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
    RecyclerView.ViewHolder(itemView) {
    private lateinit var layoutManager: LinearLayoutManager
    private var scale = 0f
    var expandThreshold = 0
    private var child: MaterialCardView = itemView.findViewById(R.id.child) as MaterialCardView
    private var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
    var startScrollTime = 0L

    init {
        //fix size
        scale =
            (PresentationUtils.getScreenWidthInPx(itemView.context) - PresentationUtils.convertDpToPixel(
                30,
                itemView.context
            )) / PresentationUtils.getScreenWidthInPx(itemView.context).toFloat()
        expandThreshold = PresentationUtils.convertDpToPixel(50, itemView.context)

        (child.layoutParams as FrameLayout.LayoutParams).height =
            ((PresentationUtils.getScreenHeightInPx(itemView.context) * 1.5f).toInt())
        child.pivotY = PresentationUtils.convertDpToPixel(15, itemView.context) / (1 - scale)
        child.pivotX = PresentationUtils.getScreenWidthInPx(itemView.context) / 2f
        child.scaleX = scale
        child.scaleY = scale

        //create and set layoutManager
        this.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //todo frist
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && System.currentTimeMillis() - startScrollTime > 500) {
                    val offset: Int = Math.abs(getTopFirstPosition())
                    if (offset < expandThreshold) {
                        recyclerView.post {
                            startScrollTime = System.currentTimeMillis()
                            smoothScroller.targetPosition = 0
                            layoutManager.startSmoothScroll(smoothScroller)
                        }
                    }

//                    else if (offset < expandThreshold) {
//                        recyclerView.post {
//                            startScrollTime = System.currentTimeMillis()
//                            layoutManager.scrollToPositionWithOffset(0, expandThreshold)
//                        }
//                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//
                val offset: Int = Math.abs(recyclerView.computeVerticalScrollOffset())
                val startScale = scale
                val finalScale = 1f
                val param =
                    child.getLayoutParams() as FrameLayout.LayoutParams
                if (expandThreshold != 0 && offset <= expandThreshold/* && firstVisibleItem == 0*/) {
                    param.height =
                        (PresentationUtils.getScreenHeightInPx(itemView.context) * 1.5f).toInt()
                    val scaleX =
                        startScale + offset / expandThreshold.toFloat() * (finalScale - startScale)
                    child.scaleX = scaleX
                    child.scaleY = scaleX
//                    child.setBackground(
//                        ThemeManager.getCurrentTheme().bd_roundedBackground(activity)
//                    )
//                    bookDetailsExpandedRunnable.onExpandChanged(true)

                    child.shapeAppearanceModel =
                        child.shapeAppearanceModel
                            .toBuilder()
                            .setTopLeftCorner(CornerFamily.ROUNDED, ((1- ( offset / expandThreshold.toFloat())) * PresentationUtils.convertDpToPixel(15, itemView.context)).toFloat())
                            .setTopRightCorner(CornerFamily.ROUNDED, ((1- ( offset / expandThreshold.toFloat())) * PresentationUtils.convertDpToPixel(15, itemView.context)).toFloat())
                            .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                            .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                            .build()

                } else {
                    param.height = FrameLayout.LayoutParams.MATCH_PARENT
                    child.scaleX = finalScale
                    child.scaleY = finalScale

                    child.shapeAppearanceModel =
                        child.shapeAppearanceModel
                            .toBuilder()
                            .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                            .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                            .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                            .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                            .build()
//                    child.setBackgroundColor(ThemeManager.getCurrentTheme().background(activity))
//                    bookDetailsExpandedRunnable.onExpandChanged(false)
                }
                child.layoutParams = param
            }
        })
    }

    var smoothScroller: SmoothScroller = object : LinearSmoothScroller(itemView.context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            if (displayMetrics != null) {
                return 300f / displayMetrics.densityDpi
            };
            return super.calculateSpeedPerPixel(displayMetrics)
        }
    }


    private fun getTopFirstPosition(): Int {
        return try {
            val v: View = recyclerView.getChildAt(0)
            v.top - child.paddingTop
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }


    fun getRecyclerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return this.adapter
    }
}