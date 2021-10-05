package com.dolatkia.horizontallycards.viewholders

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.R
import com.dolatkia.horizontallycards.databinding.RecyclerHorizontalRowBoxBinding
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
            RecyclerHorizontalRowBoxBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_1)
            1 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_2)
            2 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_3)
            3 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_4)
            4 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_5)
            5 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_6)
            6 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_7)
            7 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_8)
            8 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_9)
            9 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_10)
            10 -> return (holder as SmallBoxViewHolder).binding.image.setImageResource(R.drawable.img_11)

        }
    }

    override fun getItemCount(): Int {
        return 11;
    }
}
