package com.dolatkia.horizontallycards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycardslibrary.CardViewHolder
import com.dolatkia.horizontallycardslibrary.CartViewPagerAdapter

class MyAdapter(private val context: Context) : CartViewPagerAdapter {

    override fun onCreateCardRecyclerViewAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
       return InnerRecyclerAdapter(context)
    }

    override fun onBindCardRecyclerViewAdapter(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        position: Int
    ) {
    }


    override fun getCardsCount(): Int {
        return 50
    }

    override fun loadData() {
    }

    override fun hasMoreData(): Boolean {
        return false
    }
}


class InnerRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false)
        val holder = InnerRecyclerViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
       return 20
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}

class InnerRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}