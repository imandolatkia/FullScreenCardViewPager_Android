package com.dolatkia.horizontallycards.viewholders

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.R
import com.dolatkia.horizontallycardslibrary.PresentationUtils
import java.util.*

class HorizontalBoxesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public val recyclerView: RecyclerView = itemView as RecyclerView

    init {
        val layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HorizontalRecyclerAdapter(recyclerView.context)
    }
}

class HorizontalRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SmallBoxViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_horizontal_row_box, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SmallBoxViewHolder) {
            holder.setRandomColor()
        }
    }

    override fun getItemCount(): Int {
        return 2000;
    }
}
