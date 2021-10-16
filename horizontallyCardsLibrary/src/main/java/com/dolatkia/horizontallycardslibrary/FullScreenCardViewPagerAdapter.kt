package com.dolatkia.horizontallycardslibrary

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycardslibrary.databinding.ItemCardBinding
import com.dolatkia.horizontallycardslibrary.databinding.ItemCardLoadingBinding

abstract class FullScreenCardViewPagerAdapter(private val context: Context) :
    BaseFullScreenCardViewPagerAdapter() {

    // cached data variables
    private var cardsChangeListeners = ArrayList<CardChangeListener>()
    internal var recyclersSavedData = SparseArray<RecyclerSavedData>()
    private var viewPagerSavedPosition: Int? = null
    private var adapterList: SparseArray<RecyclerView.Adapter<RecyclerView.ViewHolder>> =
        SparseArray()

    // UI Variables
    private var isRTL = false
    private var isLoadingData = false

    // types of cards
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

            // create card view binding
            val cardBinding = ItemCardBinding.inflate(LayoutInflater.from(context), parent, false)

            // scale -1x if it's RTL
            if (isRTL) cardBinding.root.scaleX = -1f

            // create and return card view holder
            CardViewHolder(
                cardBinding,
                this,
                cardsChangeListeners,
                onCreateActionBarCustomView()
            )
        } else {

            // create and return loading card
            LoadingCardViewHolder(
                ItemCardLoadingBinding.inflate(LayoutInflater.from(context), parent, false),
                getCardRadius(context).toFloat()
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HolderType.BOOK.value) {

            // check invalid position
            if (getCardsCount() < position) {
                return
            }

            // set card position Int to CardViewHolder
            val cardViewHolder: CardViewHolder = holder as CardViewHolder
            cardViewHolder.setCardPosition(position)

            // load cached adapter or create new instance and put it in adapterList
            var cardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? =
                adapterList.get(position)
            if (cardRecyclerAdapter == null) {
                cardRecyclerAdapter = getCardRecyclerViewAdapter(position)
                adapterList.put(position, cardRecyclerAdapter)
            }

            // swap Adapter with new adapter
            cardViewHolder.getRecyclerView().swapAdapter(cardRecyclerAdapter, false)

            // update actionbar data
            cardViewHolder.getActionbarCustomView()
                ?.let { onBindActionBarCustomView(position, it) }

            // restore scroll position with recyclersSavedData (cached data)
            val recyclerSavedData = recyclersSavedData.get(position)
            if (recyclerSavedData != null) {
                cardViewHolder.setSavedData(recyclerSavedData)
            }
        } else if (!isLoadingData) {

            // set background color
            val loadingCardViewHolder: LoadingCardViewHolder = holder as LoadingCardViewHolder
            loadingCardViewHolder.setBackgroundColor(getCardsColor(position, context))

            // load data if needed
            isLoadingData = true
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

    // you should call this method every time your data loading finished
    fun dataLoaded() {
        notifyDataSetChanged()
        isLoadingData = false
    }
}