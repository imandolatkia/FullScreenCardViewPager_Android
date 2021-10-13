package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseFullScreenCardViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /*********** critical methods ************/

    // you should create your own RecyclerView.Adapter<RecyclerView.ViewHolder>
    // data in this adapter will save
    abstract fun getCardRecyclerViewAdapter(position: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>

    // return number of cards (except loading card, loading card will add with library)
    abstract fun getCardsCount(): Int

    // return View.OnClickListener to call when close button clicked
    abstract fun getOnCloseClickListener(position: Int, context: Context): View.OnClickListener

    /*********** actionbar methods (Optional) ************/

    // you should create your own actionbar custom view
    open fun onCreateActionBarCustomView(): View? {
        return null
    }

    // update actionbar data with correct data
    open fun onBindActionBarCustomView(position: Int, customView: View) {}

    /*********** endless cards methods (Optional) ************/

    // return true if you have endless cards
    // you can ignore it if you don't have endless cards
    open fun hasMoreData(): Boolean {
        return false
    }

    // load data (get from net or db)
    // you can ignore it if you don't have endless list
    open fun loadData() {}

    /*********** listener methods (Optional) ************/

    // when card scrolled vertically
    open fun onVerticalScrolled(
        recyclerView: RecyclerView,
        dy: Int,
        offset: Int,
        customActionBarView: View?
    ){}

    // when card stopped in center
    open fun onCardSelected(
        position: Int,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
    }

    open fun onCardDeselected(
        position: Int,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
    }

    /*********** UI customization methods (Optional) ************/

    // customize distance from top to enter actionbar
    open fun getActionBarStartAnimationOffsetThreshold(
        recyclerView: RecyclerView,
        customActionBarView: View?
    ): Int {
        return PresentationUtils.convertDpToPixel(50, recyclerView.context)
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
