package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ViewPagerAdapter(private val activity: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_BOOK = 0
    private val TYPE_LOADING = 1
    private var items: ArrayList<Any>? = null
    private val hasMoreData = true
    private var isLoading = false

    init {
        items = ArrayList()
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
        items?.add(Any())
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasMoreData && position == itemCount - 1) {
            TYPE_LOADING
        } else {
            TYPE_BOOK
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_BOOK) {
            val view: View =
                LayoutInflater.from(activity).inflate(R.layout.item_card, parent, false)
            view.scaleX = -1f
            return CardViewHolder(view)
        } else {
            return LoadingCardViewHolder(
                LayoutInflater.from(activity)
                    .inflate(R.layout.item_card_loading, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BOOK) {
            val booksBoxRecyclerHolder: CardViewHolder = holder as CardViewHolder
            if (items?.size!! >= position) {
//                booksBoxRecyclerHolder.setBook(items[position])
//                booksBoxRecyclerHolder.fillData()
            }
        } else if (!isLoading) {
            isLoading = true
            //todo
//            provider.getMoreDataAsync()
        }
    }

    override fun getItemCount(): Int {
        return if (items == null) {
            0
        } else items!!.size + if (hasMoreData) 1 else 0
    }
}