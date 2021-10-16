package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class FullScreenCardViewPager : FrameLayout, View.OnAttachStateChangeListener {

    // variables
    private lateinit var viewPager2: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private var isRTL = false
    private lateinit var viewPagerCardsChangeListener: ViewPagerCardChangeListener
    private var adapter: FullScreenCardViewPagerAdapter? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        // create and config view pager
        viewPager2 = ViewPager2(context)
        viewPager2.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.offscreenPageLimit = 1
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false

        // find recyclerView inside viewpager
        recyclerView = viewPager2.getChildAt(0) as RecyclerView
        recyclerView.setItemViewCacheSize(0)

        // create ViewPagerCardChangeListener for handling some animations
        viewPagerCardsChangeListener = ViewPagerCardChangeListener(viewPager2)

        // add viewPager2 to root (FrameLayout)
        addView(viewPager2)

        // set PageTransformer to translate left and right cards into the screen
        setPageTransformer()

        // for adding and removing listeners
        addOnAttachStateChangeListener(this)
    }

    /* Utilities */
    private fun setPageTransformer() {
        // set PageTransformer to translate left and right cards into the screen
        val pageOffset: Int = PresentationUtils.convertDpToPixel(25, context)
        viewPager2.setPageTransformer { view: View, position: Float ->
            if (position >= -1 && position <= 1) { // [-1,1]
                val myOffset = position * -pageOffset
                view.translationX = myOffset
            }
        }
    }

    fun setAdapter(cartViewPagerAdapter: FullScreenCardViewPagerAdapter, position: Int) {

        // set RTL
        cartViewPagerAdapter.setRTL(isRTL)

        // set cartViewPagerAdapter to viewpager
        viewPager2.adapter = cartViewPagerAdapter

        // save cartViewPagerAdapter
        adapter = cartViewPagerAdapter

        // restore viewpager position
        if (cartViewPagerAdapter.getViewPagerSavedPosition() != null) {
            cartViewPagerAdapter.getViewPagerSavedPosition()
                ?.let { viewPager2.setCurrentItem(it, false) }
        } else {
            viewPager2.setCurrentItem(position, false)
        }

        // set OnPageChangeCallback to bring left and right cards to front and call some callbacks
        viewPager2.registerOnPageChangeCallback(MyPageChangeCallback(cartViewPagerAdapter))
    }

    public override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        val tempAdapter = viewPager2.adapter
        val tempPosition = viewPager2.currentItem
        viewPager2.adapter = null
        viewPager2.adapter = tempAdapter
        viewPager2.setCurrentItem(tempPosition, false)
    }

    // setRTL does not work properly in api 28
    fun setRTL() {
        isRTL = true
        viewPager2.scaleX = -1f
    }

    override fun onViewAttachedToWindow(v: View?) {
        adapter?.addListener(viewPagerCardsChangeListener)
    }

    override fun onViewDetachedFromWindow(v: View?) {
        adapter?.removeListener(viewPagerCardsChangeListener)
    }

    inner class MyPageChangeCallback(private var cartViewPagerAdapter: FullScreenCardViewPagerAdapter) :
        OnPageChangeCallback() {

        //bring left and right cards to front and call some callbacks
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            cartViewPagerAdapter.setViewPagerSavedPosition(position)

            if (positionOffset == 0f) {
                val viewHolder: RecyclerView.ViewHolder? =
                    recyclerView.findViewHolderForAdapterPosition(position)

                if (viewHolder is CardViewHolder) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        viewHolder.itemView.outlineProvider = null
                    }
                    ViewCompat.setTranslationZ(
                        viewHolder.itemView,
                        2f
                    )
                    viewHolder.getRecyclerView().adapter?.let {
                        cartViewPagerAdapter.onCardSelected(
                            viewHolder.getCardPosition(),
                            it
                        )
                    }
                }
                val preViewHolder: RecyclerView.ViewHolder? =
                    recyclerView.findViewHolderForAdapterPosition(position - 1)
                if (preViewHolder is CardViewHolder) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        preViewHolder.itemView.outlineProvider = null
                    }
                    ViewCompat.setTranslationZ(
                        preViewHolder.itemView,
                        1f
                    )
                    preViewHolder.getRecyclerView().adapter?.let {
                        cartViewPagerAdapter.onCardDeselected(
                            preViewHolder.getCardPosition(),
                            it
                        )
                    }
                }
                val nextViewHolder: RecyclerView.ViewHolder? =
                    recyclerView.findViewHolderForAdapterPosition(position + 1)
                if (nextViewHolder is CardViewHolder) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        nextViewHolder.itemView.outlineProvider = null
                    }
                    ViewCompat.setTranslationZ(
                        nextViewHolder.itemView,
                        1f
                    )
                    nextViewHolder.getRecyclerView().adapter?.let {
                        cartViewPagerAdapter.onCardDeselected(
                            nextViewHolder.getCardPosition(),
                            it
                        )
                    }
                }
            }
        }
    }
}

