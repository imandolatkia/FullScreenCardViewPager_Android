package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class CardViewPager : FrameLayout, View.OnAttachStateChangeListener {

    protected lateinit var viewPager2: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private var isRTL = false
    private lateinit var viewPagerCardsChangeListener: ViewPagerCardsChangeListener
    private var adapter: CartViewPagerAdapter? = null

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
        viewPager2 = ViewPager2(context)
        viewPager2.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.offscreenPageLimit = 1
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false

        recyclerView = viewPager2.getChildAt(0) as RecyclerView
        recyclerView.setItemViewCacheSize(0)

        viewPagerCardsChangeListener = ViewPagerCardsChangeListener(viewPager2)
        addView(viewPager2)
        setPageTransformer()
        addOnAttachStateChangeListener(this)
    }

    /* Utilities */
    private fun setPageTransformer() {
        //10dp
        val pageOffset: Int = PresentationUtils.convertDpToPixel(25, context)
        viewPager2.setPageTransformer { view: View, position: Float ->
            if (position >= -1 && position <= 1) { // [-1,1]
                val myOffset = position * -pageOffset
                view.translationX = myOffset
            }
        }
    }

    fun setAdapter(cartViewPagerAdapter: CartViewPagerAdapter, position: Int) {
        cartViewPagerAdapter.setRTL(isRTL)
        viewPager2.adapter = cartViewPagerAdapter
        cartViewPagerAdapter.addListener(viewPagerCardsChangeListener)
        adapter = cartViewPagerAdapter

        if (cartViewPagerAdapter.getViewPagerSavedPosition() != null) {
            cartViewPagerAdapter.getViewPagerSavedPosition()?.let { viewPager2.setCurrentItem(it, false) }
        } else {
            viewPager2.setCurrentItem(position, false)
        }

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                cartViewPagerAdapter.setViewPagerSavedPosition(position)

                if (positionOffset == 0f) {
                    val viewHolder: RecyclerView.ViewHolder? =
                            recyclerView?.findViewHolderForAdapterPosition(position)

                    if (viewHolder is CardViewHolder) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            viewHolder.itemView.outlineProvider = null
                        }
                        ViewCompat.setTranslationZ(
                                viewHolder.itemView,
                                2f
                        )
                        viewHolder.recyclerView.adapter?.let { cartViewPagerAdapter.onCardSelected(viewHolder.getCardPosition(), it) }
                    }
                    val preViewHolder: RecyclerView.ViewHolder? =
                            recyclerView?.findViewHolderForAdapterPosition(position - 1)
                    if (preViewHolder is CardViewHolder) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            preViewHolder.itemView.outlineProvider = null
                        }
                        ViewCompat.setTranslationZ(
                                preViewHolder.itemView,
                                1f
                        )
                        preViewHolder.recyclerView.adapter?.let { cartViewPagerAdapter.onCardDeselected(preViewHolder.getCardPosition(), it) }
                    }
                    val nextViewHolder: RecyclerView.ViewHolder? =
                            recyclerView?.findViewHolderForAdapterPosition(position + 1)
                    if (nextViewHolder is CardViewHolder) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            nextViewHolder.itemView.outlineProvider = null
                        }
                        ViewCompat.setTranslationZ(
                                nextViewHolder.itemView,
                                1f
                        )
                        nextViewHolder.recyclerView.adapter?.let { cartViewPagerAdapter.onCardDeselected(nextViewHolder.getCardPosition(), it) }
                    }
                }
            }
        })
    }

    fun onConfigurationChanged() {
        val tempAdapter = viewPager2.adapter
        val tempPosition = viewPager2.currentItem
        viewPager2.adapter = null
        viewPager2.adapter = tempAdapter
        viewPager2.setCurrentItem(tempPosition, false)
    }

    public fun setRTL() {
        isRTL = true
        viewPager2.scaleX = -1f
    }

    override fun onViewAttachedToWindow(v: View?) {
        adapter?.addListener(viewPagerCardsChangeListener)

    }

    override fun onViewDetachedFromWindow(v: View?) {
        adapter?.removeListener(viewPagerCardsChangeListener)
    }
}

