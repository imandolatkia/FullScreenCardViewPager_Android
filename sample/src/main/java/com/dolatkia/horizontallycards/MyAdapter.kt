package com.dolatkia.horizontallycards

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycardslibrary.CartViewPagerAdapter
import com.google.android.material.card.MaterialCardView
import java.util.*

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

    val TYPE_ROW_BOX = 0
    val TYPE_ROW_TEXT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType){
            TYPE_ROW_BOX -> {return InnerRecyclerBoxViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_row_box, parent, false))}
            TYPE_ROW_TEXT -> return InnerRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false))
        }
        return InnerRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false))
    }

    override fun getItemCount(): Int {
       return 2000
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if(holder is InnerRecyclerBoxViewHolder){
           holder.box.setCardBackgroundColor(generateRandomColor())
       }
    }

    val mRandom: Random = Random(System.currentTimeMillis())

    fun generateRandomColor(): Int {
        // This is the base color which will be mixed with the generated one
        val baseColor: Int = Color.WHITE
        val baseRed: Int = Color.red(baseColor)
        val baseGreen: Int = Color.green(baseColor)
        val baseBlue: Int = Color.blue(baseColor)
        val red: Int = (baseRed + mRandom.nextInt(256)) / 2
        val green: Int = (baseGreen + mRandom.nextInt(256)) / 2
        val blue: Int = (baseBlue + mRandom.nextInt(256)) / 2
        return Color.rgb(red, green, blue)
    }

    override fun getItemViewType(position: Int): Int {
        when (position){
             0 -> return TYPE_ROW_BOX
             1 -> return TYPE_ROW_TEXT
        }
        return TYPE_ROW_BOX
    }
}

class InnerRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

class InnerRecyclerBoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public val box : MaterialCardView = itemView.findViewById(R.id.card)
}