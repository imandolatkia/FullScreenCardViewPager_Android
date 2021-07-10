package com.dolatkia.horizontallycardslibrary

import androidx.recyclerview.widget.RecyclerView

interface CartViewPagerAdapter {
    fun onCreateCardRecyclerViewAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>
    fun onBindCardRecyclerViewAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, position: Int)
    fun getCardsCount(): Int
    fun loadData()
    fun hasMoreData(): Boolean
}