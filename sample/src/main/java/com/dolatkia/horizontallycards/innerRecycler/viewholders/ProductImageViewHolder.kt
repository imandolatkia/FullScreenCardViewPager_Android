package com.dolatkia.horizontallycards.innerRecycler.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.databinding.RecyclerTopImageBinding

class ProductImageViewHolder(private var binding: RecyclerTopImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setImage(resId: Int) {
        binding.image.setImageResource(resId)
    }

}