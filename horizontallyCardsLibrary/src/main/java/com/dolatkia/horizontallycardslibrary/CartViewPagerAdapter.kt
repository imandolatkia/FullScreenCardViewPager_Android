package com.dolatkia.horizontallycardslibrary

import androidx.recyclerview.widget.RecyclerView

abstract class CartViewPagerAdapter {

    private var adapter: ViewPagerAdapter? = null

    abstract fun onCreateCardRecyclerViewAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>
    abstract fun onBindCardRecyclerViewAdapter(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        position: Int
    )

    abstract fun getCardsCount(): Int
    abstract fun loadData()
    abstract fun hasMoreData(): Boolean
    protected fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }


    fun setViewPagerAdapterAdapter(adapter: ViewPagerAdapter) {
        this.adapter = adapter
    }
}
