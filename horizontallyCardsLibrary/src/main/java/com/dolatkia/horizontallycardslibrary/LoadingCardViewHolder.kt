package com.dolatkia.horizontallycardslibrary

import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycardslibrary.databinding.ItemCardLoadingBinding
import com.google.android.material.shape.CornerFamily

class LoadingCardViewHolder(var binding: ItemCardLoadingBinding, topRadius: Float) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        setTopRadius(topRadius)
    }

    fun setBackgroundColor(color: Int) {
        binding.card.setCardBackgroundColor(color)
    }

    private fun setTopRadius(radius: Float) {
        binding.card.shapeAppearanceModel =
            binding.card.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(
                    CornerFamily.ROUNDED,
                    radius
                )
                .setTopRightCorner(
                    CornerFamily.ROUNDED,
                    radius
                )
                .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                .build()
    }
}