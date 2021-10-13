package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class FullScreenCardViewPagerAdapter(private val context: Context) :
    BaseFullScreenCardViewPagerAdapter() {

    private var cardsChangeListeners = ArrayList<CardChangeListener>()
    internal var recyclersSavedData = SparseArray<RecyclerSavedData>()
    private var viewPagerSavedPosition: Int? = null
    private var adapterList: SparseArray<RecyclerView.Adapter<RecyclerView.ViewHolder>> =
        SparseArray()
    private var isRTL = false
    private var isloadingData = false

    enum class HolderType(val value: Int) {
        BOOK(0), LOADING(1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasMoreData() && position == itemCount - 1) {
            HolderType.LOADING.value
        } else {
            HolderType.BOOK.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HolderType.BOOK.value) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
            if (isRTL) view.scaleX = -1f
            val holder = CardViewHolder(view as ViewGroup, this, cardsChangeListeners)
            holder.setActionBarCustomView(onCreateActionBarCustomView())
            holder
        } else {
            LoadingCardViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_card_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HolderType.BOOK.value) {
            if (getCardsCount() >= position) {
                val cardViewHolder: CardViewHolder = holder as CardViewHolder
                cardViewHolder.setCardPosition(position)
                var cardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? =
                    adapterList.get(position)
                if (cardRecyclerAdapter == null) {
                    cardRecyclerAdapter = getCardRecyclerViewAdapter(position)
                    adapterList.put(position, cardRecyclerAdapter)
                }
                cardViewHolder.recyclerView.swapAdapter(cardRecyclerAdapter, false)
                cardViewHolder.getActionbarCustomView()
                    ?.let { onBindActionBarCustomView(position, it) }
                val recyclerSavedData = recyclersSavedData.get(position)
                if (recyclerSavedData != null) {
                    cardViewHolder.setSavedData(recyclerSavedData)
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

    fun addListener(cardChangeListener: CardChangeListener) {
        if (!cardsChangeListeners.contains(cardChangeListener)) {
            this.cardsChangeListeners.add(cardChangeListener)
        }
    }

    fun removeListener(cardChangeListener: CardChangeListener) {
        if (cardsChangeListeners.contains(cardChangeListener)) {
            this.cardsChangeListeners.remove(cardChangeListener)
        }
    }

    fun getViewPagerSavedPosition(): Int? {
        return viewPagerSavedPosition
    }

    fun setViewPagerSavedPosition(position: Int) {
        viewPagerSavedPosition = position
    }

    internal fun setRTL(isRTL: Boolean) {
        this.isRTL = isRTL
    }

    fun dataLoaded() {
        notifyDataSetChanged()
        isloadingData = false
    }
}