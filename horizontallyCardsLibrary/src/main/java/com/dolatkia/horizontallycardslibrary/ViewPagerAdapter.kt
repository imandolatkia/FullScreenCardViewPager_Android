package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ViewPagerAdapter(
    private val activity: Context,
    val cartViewPagerAdapter: CartViewPagerAdapter,
    val expandedRunnable: ExpandedRunnable
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_BOOK = 0
    private val TYPE_LOADING = 1
    private val hasMoreData = true
    private var isLoading = false

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
            return CardViewHolder(view as ViewGroup, cartViewPagerAdapter.onCreateCardRecyclerViewAdapter(), expandedRunnable)
        } else {
            return LoadingCardViewHolder(
                LayoutInflater.from(activity)
                    .inflate(R.layout.item_card_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BOOK) {
            if (cartViewPagerAdapter.getCardsCount() >= position) {
                val holder: CardViewHolder = holder as CardViewHolder
                cartViewPagerAdapter.onBindCardRecyclerViewAdapter(holder.getRecyclerAdapter(), position)
            }
        } else {
//            isLoading = true
            cartViewPagerAdapter.loadData()
        }
    }

    override fun getItemCount(): Int {
        return cartViewPagerAdapter.getCardsCount() + if (cartViewPagerAdapter.hasMoreData()) 1 else 0
    }
}