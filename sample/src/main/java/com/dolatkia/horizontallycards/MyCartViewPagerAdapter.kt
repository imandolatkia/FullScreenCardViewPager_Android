package com.dolatkia.horizontallycards

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.databinding.ItemActionbarBinding
import com.dolatkia.horizontallycards.viewholders.BoxViewHolder
import com.dolatkia.horizontallycards.viewholders.HorizontalBoxesViewHolder
import com.dolatkia.horizontallycards.viewholders.TextViewHolder
import com.dolatkia.horizontallycardslibrary.CartViewPagerAdapter

class MyCartViewPagerAdapter(private val activity: Activity) : CartViewPagerAdapter(activity) {

    private val itemTouchHelper = arrayListOf<Int>()

    init {
        addFakeItems()
    }

    override fun getCardRecyclerViewAdapter(position: Int): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return InnerRecyclerAdapter(activity, position)
    }

    override fun getCardsCount(): Int {
        return itemTouchHelper.size
    }

    override fun onCreateActionBarCustomView(): View? {
        return ItemActionbarBinding.inflate(activity.layoutInflater).root
    }

    override fun onBindActionBarCustomView(position: Int, customView: View) {
        ItemActionbarBinding.bind(customView).title.text = "Title "+(position+1).toString()
    }

    override fun onVerticalScrolled(
        recyclerView: RecyclerView,
        dy: Int,
        offset: Int,
        customActionBarView: View?
    ) {
    }

    override fun getOnCloseClickListener(position: Int, context: Context): View.OnClickListener {
        return View.OnClickListener { activity.onBackPressed() }
    }

    fun addFakeItems() {
        for (i in 0..5) {
            itemTouchHelper.add(0)
        }
    }

    override fun loadData() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                addFakeItems()
                notifyDataSetChanged()
                dataLoaded()
            },
            1000 // value in milliseconds
        )
    }

    override fun hasMoreData(): Boolean {
        return true
    }
}


class InnerRecyclerAdapter(private val context: Context, private var cardPosition: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_ROW_SINGLE_BOX = 0
    val TYPE_ROW_TEXT = 1
    val TYPE_ROW_HORIZONTAL_BOXES = 2
    val TYPE_ROW_FULL_BOXES = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_ROW_SINGLE_BOX -> {
                return BoxViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.recycler_row_box, parent, false)
                )
            }
            TYPE_ROW_TEXT -> return TextViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_row_text, parent, false)
            )

            TYPE_ROW_FULL_BOXES -> return BoxViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_row_full_box, parent, false)
            )

        }
        return HorizontalBoxesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_row_boxes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 2000
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BoxViewHolder) {
            holder.setRandomColor()
            holder.num?.text = (this.cardPosition + 1).toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_ROW_SINGLE_BOX
        }

        when ((position - 1) % 3) {
            0 -> return TYPE_ROW_TEXT
            1 -> return TYPE_ROW_HORIZONTAL_BOXES
            2 -> return TYPE_ROW_FULL_BOXES
        }
        return TYPE_ROW_HORIZONTAL_BOXES
    }
}


