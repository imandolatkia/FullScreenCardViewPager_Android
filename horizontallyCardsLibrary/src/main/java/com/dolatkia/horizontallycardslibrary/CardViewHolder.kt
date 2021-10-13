package com.dolatkia.horizontallycardslibrary

import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import kotlin.math.abs


class CardViewHolder(
    private var rootView: ViewGroup,
    private val cartViewPagerAdapter: FullScreenCardViewPagerAdapter,
    private val expandedRunnable: ArrayList<CardChangeListener>
) :
    RecyclerView.ViewHolder(rootView) {
    private var layoutManager: LinearLayoutManager
    private var scale = 0f
    private var cardBeforeHeight = 0f
    var expandThreshold = 0
    private var child: MaterialCardView = itemView.findViewById(R.id.child) as MaterialCardView
    var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
    var startScrollTime = 0L
    var cardActionBar: CardActionBar? = null
    private var actionBarVisibleThreshold = -1
    private var cardPosition: Int = 0
    private var isExpanded = false

    init {
        //fix size
        scale =
            (PresentationUtils.getScreenWidthInPx(itemView.context) - PresentationUtils.convertDpToPixel(
                30,
                itemView.context
            )) / PresentationUtils.getScreenWidthInPx(itemView.context).toFloat()
        expandThreshold = PresentationUtils.convertDpToPixel(50, itemView.context)

        cardBeforeHeight = PresentationUtils.getScreenHeightInPx(itemView.context) / scale
        (child.layoutParams as FrameLayout.LayoutParams).height = cardBeforeHeight.toInt()
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


        child.setCardBackgroundColor(
            cartViewPagerAdapter.getCardsColor(
                cardPosition,
                child.context
            )
        )

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && System.currentTimeMillis() - startScrollTime > 500) {
                    val offset: Int = getTopOffset()
                    if (offset < expandThreshold) {
                        recyclerView.post {
                            startScrollTime = System.currentTimeMillis()
                            smoothScroller.targetPosition = 0
                            layoutManager.startSmoothScroll(smoothScroller)
                        }
                    }

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy == 0) {
                    return
                }

                cartViewPagerAdapter.onVerticalScrolled(
                    recyclerView,
                    dy,
                    getTopOffset(),
                    cardActionBar?.customView
                )
                onScroll()
            }

        })

        setTopRadius(cartViewPagerAdapter.getCardRadius(itemView.context).toFloat())
    }

    private fun setTopRadius(radius: Float) {
        child.shapeAppearanceModel =
            child.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(
                    CornerFamily.ROUNDED,
                    radius
                )
                .setTopRightCorner(
                    CornerFamily.ROUNDED,
                    radius
                )
                .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                .build()
    }

    fun onScroll() {
        val offset: Int = getTopOffset()
        setCardScale(offset)
        val isActionbarVisible = updateActionBar(offset)
        cartViewPagerAdapter.recyclersSavedData.put(
            cardPosition,
            recyclerView.layoutManager?.onSaveInstanceState()?.let {
                RecyclerSavedData(
                    it,
                    actionBarVisibleThreshold,
                    isExpanded,
                    isActionbarVisible
                )
            })
    }

    private fun setCardScale(offset: Int) {
        val startScale = scale
        val finalScale = 1f
        val param =
            child.layoutParams as FrameLayout.LayoutParams

        if (expandThreshold != 0 && offset <= expandThreshold) {

            if (isExpanded) {
                notifyOnExpandChanged(false)
                isExpanded = false
            }

            param.height = cardBeforeHeight.toInt()
            val scaleX =
                startScale + offset / expandThreshold.toFloat() * (finalScale - startScale)
            child.scaleX = scaleX
            child.scaleY = scaleX

            notifyOnScroll(offset)
            setTopRadius(
                (1 - (offset / expandThreshold.toFloat())) * cartViewPagerAdapter.getCardRadius(
                    itemView.context
                )
            )
        } else {

            if (!isExpanded) {
                notifyOnExpandChanged(true)
                isExpanded = true
            }

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
        }
        child.layoutParams = param
    }

    private fun updateActionBar(offset: Int): Boolean {
        if (actionBarVisibleThreshold == -1) {
            actionBarVisibleThreshold =
                cartViewPagerAdapter.getActionBarStartAnimationOffsetThreshold(
                    recyclerView,
                    cardActionBar?.customView
                )
        }
        return cardActionBar?.updateUI(offset, actionBarVisibleThreshold) == true
    }

    var smoothScroller: SmoothScroller = object : LinearSmoothScroller(itemView.context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            if (displayMetrics != null) {
                return 300f / displayMetrics.densityDpi
            }
            return super.calculateSpeedPerPixel(displayMetrics)
        }
    }

    fun setActionBarCustomView(view: View?) {
        if (view == null) {
            return
        }
        cardActionBar = CardActionBar(itemView.context, view)
        cardActionBar?.actionbarBinding?.closeImage?.setImageResource(
            cartViewPagerAdapter.getCloseResId(
                cardPosition,
                child.context
            )
        )
        cardActionBar?.actionbarBinding?.closeImage?.setColorFilter(
            cartViewPagerAdapter.getCloseColor(
                cardPosition,
                child.context
            )
        )
        cardActionBar?.actionbarBinding?.closeImage?.setOnClickListener(
            cartViewPagerAdapter.getOnCloseClickListener(
                cardPosition,
                child.context
            )
        )
        ViewCompat.setTranslationZ(
            cardActionBar!!.getView(),
            60f
        )
        this.rootView.addView(
            cardActionBar!!.getView(),
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    fun getActionbarCustomView(): View? {
        return cardActionBar?.customView
    }

    fun setCardPosition(cardPosition: Int) {
        this.cardPosition = cardPosition
    }

    fun getCardPosition(): Int {
        return this.cardPosition
    }

    fun setSavedData(savedData: RecyclerSavedData) {
        recyclerView.layoutManager?.onRestoreInstanceState(savedData.parcelable)
        actionBarVisibleThreshold = savedData.actionBarVisibleThreshold
        if (savedData.isExpanded) {
            setCardScale(Int.MAX_VALUE)
        } else {
            setCardScale(0)
        }

        if (savedData.isActionbarVisible) {
            updateActionBar(Int.MAX_VALUE)
        } else {
            updateActionBar(0)
        }
    }

    private fun notifyOnScroll(offset: Int) {
        expandedRunnable.forEach {
            it.onScroll(offset)
        }
    }

    private fun notifyOnExpandChanged(isExpanded: Boolean) {
        expandedRunnable.forEach {
            it.onExpandChanged(isExpanded)
        }
    }

    fun getTopOffset(): Int {
        val manager = recyclerView.layoutManager as LinearLayoutManager? ?: return 0
        val firstItemView = manager.findViewByPosition(0) ?: return Int.MAX_VALUE
        return abs(firstItemView.top)
    }
}