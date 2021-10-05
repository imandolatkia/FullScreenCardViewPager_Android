package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class CartViewPagerAdapter(private val context: Context) : BaseCartViewPagerAdapter() {

    public companion object {
        val TYPE_BOOK = 0
        val TYPE_LOADING = 1
    }

    private var cardsChangeListeners = ArrayList<CardsChangeListener>()

    private val hasMoreData = true
    private var isLoading = false
    public var recyclersSavedData = SparseArray<RecyclerSavedData>()
    private var viewPagerSavedPosition: Int? = null
    var adapterList: SparseArray<RecyclerView.Adapter<RecyclerView.ViewHolder>> = SparseArray()
    private var isRTL = false
    private var isloadingData = false

    override fun getItemViewType(position: Int): Int {
        return if (hasMoreData() && position == itemCount - 1) {
            TYPE_LOADING
        } else {
            TYPE_BOOK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_BOOK) {
            Log.d("checkPool", "pager one")
            val view: View =
                    LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
            if (isRTL) view.scaleX = -1f
            val holder = CardViewHolder(view as ViewGroup, this, cardsChangeListeners, parent.height)
            holder.setActionBarCustomView(onCreateActionBarCustomView())
            return holder
        } else {
            return LoadingCardViewHolder(
                    LayoutInflater.from(context)
                            .inflate(R.layout.item_card_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BOOK) {
            if (getCardsCount() >= position) {
                val holder: CardViewHolder = holder as CardViewHolder
                holder.setCardPosition(position)
                var cardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = adapterList.get(position)
                if (cardRecyclerAdapter == null) {
                    cardRecyclerAdapter = getCardRecyclerViewAdapter(position)
                    adapterList.put(position, cardRecyclerAdapter)
                }
                holder.recyclerView.swapAdapter(cardRecyclerAdapter, false)
                holder.getActionbarCustomView()?.let { onBindActionBarCustomView(position, it) }
                val recyclerSavedData = recyclersSavedData.get(position)
                if (recyclerSavedData != null) {
                    holder.setSavedData(recyclerSavedData)
                }
            }
        } else if (!isloadingData) {
            isloadingData = true
            loadData()
        }
    }

    override fun getItemCount(): Int {
        return getCardsCount() + if (hasMoreData()) 1 else 0
    }

    fun addListener(cardsChangeListener: CardsChangeListener) {
        if (!cardsChangeListeners.contains(cardsChangeListener)) {
            this.cardsChangeListeners.add(cardsChangeListener)
        }
    }

    fun removeListener(cardsChangeListener: CardsChangeListener) {
        if (cardsChangeListeners.contains(cardsChangeListener)) {
            this.cardsChangeListeners.remove(cardsChangeListener)
        }
    }

    public fun getViewPagerSavedPosition(): Int? {
        return viewPagerSavedPosition
    }

    public fun setViewPagerSavedPosition(position: Int) {
        viewPagerSavedPosition = position
    }

    public fun setRTL(isRTL: Boolean) {
        this.isRTL = isRTL
    }

    public fun dataLoaded() {
        isloadingData = false
    }
}