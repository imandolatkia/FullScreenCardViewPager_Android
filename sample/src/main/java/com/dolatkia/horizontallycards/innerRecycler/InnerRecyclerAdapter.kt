package com.dolatkia.horizontallycards.innerRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.R
import com.dolatkia.horizontallycards.databinding.RecyclerRowNameAndPriceBinding
import com.dolatkia.horizontallycards.databinding.RecyclerTopImageBinding
import com.dolatkia.horizontallycards.innerRecycler.innerRecyclerHorizontalRecycler.InnerRecycleHorizontalBoxesViewHolder
import com.dolatkia.horizontallycards.innerRecycler.viewholders.GeneralViewHolder
import com.dolatkia.horizontallycards.innerRecycler.viewholders.ProductImageViewHolder
import com.dolatkia.horizontallycards.innerRecycler.viewholders.ProductNameAndPriceViewHolder
import com.dolatkia.horizontallycards.model.Product

class InnerRecyclerAdapter(
    private val context: Context,
    private var cardPosition: Int,
    private var product: Product
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class HolderType(val value: Int) {
        ROW_IMAGE(0), ROW_TEXT(1), ROW_HORIZONTAL_BOXES(2), ROW_NAME_AND_PRICE(3), ADD_TO_CHART(4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            HolderType.ROW_IMAGE.value -> {
                return ProductImageViewHolder(
                    RecyclerTopImageBinding.inflate(LayoutInflater.from(context), parent, false)
                )
            }

            HolderType.ADD_TO_CHART.value -> return GeneralViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.recycler_row_add_to_chart, parent, false)
            )

            HolderType.ROW_TEXT.value -> return GeneralViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_row_text, parent, false)
            )

            HolderType.ROW_NAME_AND_PRICE.value -> return ProductNameAndPriceViewHolder(
                RecyclerRowNameAndPriceBinding.inflate(LayoutInflater.from(context), parent, false)
            )
        }
        return InnerRecycleHorizontalBoxesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_row_boxes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductImageViewHolder) {
            holder.setImage(product.imageRes)

        } else if (holder is ProductNameAndPriceViewHolder) {
            holder.setData(product, cardPosition)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HolderType.ROW_IMAGE.value
            1 -> HolderType.ROW_NAME_AND_PRICE.value
            2 -> HolderType.ADD_TO_CHART.value
            3 -> HolderType.ROW_TEXT.value
            4 -> HolderType.ROW_HORIZONTAL_BOXES.value
            5 -> HolderType.ROW_TEXT.value
            6 -> HolderType.ROW_IMAGE.value
            else -> HolderType.ADD_TO_CHART.value
        }
    }
}