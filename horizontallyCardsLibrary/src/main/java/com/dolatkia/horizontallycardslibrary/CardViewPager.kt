package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class CardViewPager : FrameLayout {

    private lateinit var viewPager2: ViewPager2
    private var adapter: ViewPagerAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null

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
        viewPager2.offscreenPageLimit = 2
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
//        viewPager2.scaleX = -1f
        addView(viewPager2)
        setPageTransformer()
    }

    /* Utilities */
    private fun setPageTransformer() {
        //10dp
        val pageOffset: Int = PresentationUtils.convertDpToPixel(25, context)
//        val pageOffset: Int = 100
        viewPager2.setPageTransformer { view: View, position: Float ->
            if (position >= -1 && position <= 1) { // [-1,1]
                val myOffset = position * -pageOffset
                view.translationX = myOffset
            }
        }
    }

    public fun setAdapter(cartViewPagerAdapter: CartViewPagerAdapter) {
        if (adapter == null) {
            adapter = ViewPagerAdapter(context, cartViewPagerAdapter)
        }
        viewPager2.adapter = adapter
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
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
                        //todo
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
                    }
                }
            }
        })
        recyclerView = viewPager2.getChildAt(0) as RecyclerView
        if (recyclerView != null) {
            linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
            if (linearLayoutManager != null) {
                //todo
//                linearLayoutManager?.scrollToPosition(position)
            }
        }
    }
}