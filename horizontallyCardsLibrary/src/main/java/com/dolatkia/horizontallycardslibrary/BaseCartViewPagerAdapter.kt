package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseCartViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    abstract fun getCardRecyclerViewAdapter(position: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>
    abstract fun getCardsCount(): Int
    abstract fun onCreateActionBarCustomView(): View?
    abstract fun onBindActionBarCustomView(position: Int, customView: View)
    abstract fun onVerticalScrolled(recyclerView: RecyclerView, dy: Int, offset: Int, customActionBarView: View?)
    abstract fun getOnCloseClickListener(position: Int, context: Context): View.OnClickListener
    open fun onCardSelected(position: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {}
    open fun onCardDeselected(position: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {}

    open fun getActionBarStartAnimationOffsetThreshold(recyclerView: RecyclerView, customActionBarView: View?): Int {
        return  PresentationUtils.convertDpToPixel(50, recyclerView.context)
    }

    open fun loadData() {}
    open fun hasMoreData(): Boolean {
        return false
    }

    open fun getCardsColor(position: Int, context: Context): Int {
        return Color.parseColor("#ffffff")
    }

    open fun getCardRadius(context: Context): Int {
        return PresentationUtils.convertDpToPixel(15, context)
    }

    open fun getCloseResId(position: Int, context: Context): Int {
        return R.drawable.ic_close
    }

    open fun getCloseColor(position: Int, context: Context): Int {
        return Color.parseColor("#444444")
    }
}
