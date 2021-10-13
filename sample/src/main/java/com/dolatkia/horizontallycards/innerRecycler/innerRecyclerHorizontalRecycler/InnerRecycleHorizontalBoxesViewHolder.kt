package com.dolatkia.horizontallycards.innerRecycler.innerRecyclerHorizontalRecycler

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InnerRecycleHorizontalBoxesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recyclerView: RecyclerView = itemView as RecyclerView

    init {
        val layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = InnerRecycleHorizontalRecyclerAdapter(recyclerView.context)
    }
}
