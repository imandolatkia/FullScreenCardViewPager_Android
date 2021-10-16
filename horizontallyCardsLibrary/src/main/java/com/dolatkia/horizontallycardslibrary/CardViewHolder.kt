package com.dolatkia.horizontallycardslibrary

import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.dolatkia.horizontallycardslibrary.databinding.ItemCardBinding
import com.google.android.material.shape.CornerFamily
import kotlin.math.abs


class CardViewHolder(
    private var cardBinding: ItemCardBinding,
    private val cartViewPagerAdapter: FullScreenCardViewPagerAdapter,
    private val expandedRunnable: ArrayList<CardChangeListener>,
    customActionbarView: View?
) :
    RecyclerView.ViewHolder(cardBinding.root) {

    // views variables
    private var layoutManager: LinearLayoutManager
    private var cardActionBar: CardActionBar? = null

    // animation variables
    private var scale = 0f
    private var cardBeforeHeight = 0f
    private var expandThreshold = 0
    private var startScrollTime = 0L
    private var actionBarVisibleThreshold = -1
    private var cardPosition: Int = 0
    private var isExpanded = false

    init {
        //initialize variables
        scale =
            (PresentationUtils.getScreenWidthInPx(itemView.context) - PresentationUtils.convertDpToPixel(
                30,
                itemView.context
            )) / PresentationUtils.getScreenWidthInPx(itemView.context).toFloat()
        expandThreshold = PresentationUtils.convertDpToPixel(50, itemView.context)
        cardBeforeHeight = PresentationUtils.getScreenHeightInPx(itemView.context) / scale

        // config cardView layout
        (cardBinding.card.layoutParams as FrameLayout.LayoutParams).height =
            cardBeforeHeight.toInt()
        cardBinding.card.pivotY =
            PresentationUtils.convertDpToPixel(15, itemView.context) / (1 - scale)
        cardBinding.card.pivotX = PresentationUtils.getScreenWidthInPx(itemView.context) / 2f
        cardBinding.card.scaleX = scale
        cardBinding.card.scaleY = scale

        //create and set layoutManager
        this.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.VERTICAL, false
        )
        cardBinding.recyclerView.layoutManager = layoutManager

        // set cardView background color
        cardBinding.card.setCardBackgroundColor(
            cartViewPagerAdapter.getCardsColor(
                cardPosition,
                cardBinding.card.context
            )
        )

        // add ScrollListener to handle animations
        cardBinding.recyclerView.addOnScrollListener(MyScrollListener())

        // set card top corner radius
        setTopRadius(cartViewPagerAdapter.getCardRadius(itemView.context).toFloat())

        // setActionBarCustomView
        setActionBarCustomView(customActionbarView)
    }

    private fun setTopRadius(radius: Float) {
        cardBinding.card.shapeAppearanceModel =
            cardBinding.card.shapeAppearanceModel
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

    private fun setActionBarCustomView(view: View?) {

        // ignore if there is no custom actionbar view
        if (view == null) {
            return
        }

        // create CardActionBar
        cardActionBar = CardActionBar(itemView.context, view)

        // set close image res
        cardActionBar?.setCloseImageRes(
            cartViewPagerAdapter.getCloseResId(
                cardPosition,
                cardBinding.card.context
            )
        )

        // set close color
        cardActionBar?.setCloseImageColor(
            cartViewPagerAdapter.getCloseColor(
                cardPosition,
                cardBinding.card.context
            )
        )

        // set close click listener
        cardActionBar?.setCLoseClickListener(
            cartViewPagerAdapter.getOnCloseClickListener(
                cardPosition,
                cardBinding.card.context
            )
        )

        // add cardActionBar to view
        cardBinding.container.addView(
            cardActionBar!!.getView(),
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun setCardScale(offset: Int) {
        val startScale = scale
        val finalScale = 1f
        val param = cardBinding.card.layoutParams as FrameLayout.LayoutParams

        if (expandThreshold != 0 && offset <= expandThreshold) {

            // call onExpandChanged callback
            if (isExpanded) {
                notifyOnExpandChanged(false)
                isExpanded = false
            }

            // set unexpanded card config depends on top offset
            param.height = cardBeforeHeight.toInt()
            val scaleX =
                startScale + offset / expandThreshold.toFloat() * (finalScale - startScale)
            cardBinding.card.scaleX = scaleX
            cardBinding.card.scaleY = scaleX
            setTopRadius(
                (1 - offset / expandThreshold.toFloat()) * cartViewPagerAdapter.getCardRadius(
                    itemView.context
                )
            )

            // call onScroll callback
            notifyOnScroll(offset)

        } else {

            // call onExpandChanged callback
            if (!isExpanded) {
                notifyOnExpandChanged(true)
                isExpanded = true
            }

            // sett expanded card config
            param.height = FrameLayout.LayoutParams.MATCH_PARENT
            cardBinding.card.scaleX = finalScale
            cardBinding.card.scaleY = finalScale
            cardBinding.card.shapeAppearanceModel =
                cardBinding.card.shapeAppearanceModel
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                    .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                    .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                    .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                    .build()
        }

        // set layout param
        cardBinding.card.layoutParams = param
    }

    private fun updateActionBar(offset: Int): Boolean {
        if (actionBarVisibleThreshold == -1) {
            actionBarVisibleThreshold =
                cartViewPagerAdapter.getActionBarStartAnimationOffsetThreshold(
                    cardBinding.recyclerView,
                    cardActionBar?.customView
                )
        }
        return cardActionBar?.updateUI(offset, actionBarVisibleThreshold) == true
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

    private fun getTopOffset(): Int {
        val manager = cardBinding.recyclerView.layoutManager as LinearLayoutManager? ?: return 0
        val firstItemView = manager.findViewByPosition(0) ?: return Int.MAX_VALUE
        return abs(firstItemView.top)
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
        cardBinding.recyclerView.layoutManager?.onRestoreInstanceState(savedData.parcelable)
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

    fun getRecyclerView(): RecyclerView {
        return cardBinding.recyclerView
    }

    inner class MyScrollListener : RecyclerView.OnScrollListener() {
        private var smoothScroller: SmoothScroller =
            object : LinearSmoothScroller(itemView.context) {
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

        private fun onScroll() {
            val offset: Int = getTopOffset()
            setCardScale(offset)
            val isActionbarVisible = updateActionBar(offset)
            cartViewPagerAdapter.recyclersSavedData.put(
                cardPosition,
                cardBinding.recyclerView.layoutManager?.onSaveInstanceState()?.let {
                    RecyclerSavedData(
                        it,
                        actionBarVisibleThreshold,
                        isExpanded,
                        isActionbarVisible
                    )
                })
        }
    }
}