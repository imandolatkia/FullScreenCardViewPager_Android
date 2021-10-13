package com.dolatkia.horizontallycards.innerRecycler.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.databinding.RecyclerRowNameAndPriceBinding
import com.dolatkia.horizontallycards.model.Product

class ProductNameAndPriceViewHolder(private var binding: RecyclerRowNameAndPriceBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(product: Product, cardPosition: Int) {
        binding.price.text = product.price
        ("Beautiful Chair " + (cardPosition + 1).toString()).also {
            binding.title.text = it
        }
    }
}