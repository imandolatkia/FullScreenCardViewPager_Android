package com.dolatkia.horizontallycardslibrary

import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var scale = 0f
    var expandThreshold = 0
    private var child: View = itemView.findViewById(R.id.child)

    init {
        //fix size
        scale =
            (PresentationUtils.getScreenWidthInPx(itemView.context) - PresentationUtils.convertDpToPixel(
                30,
                itemView.context
            )) / PresentationUtils.getScreenWidthInPx(itemView.context).toFloat()
        expandThreshold = PresentationUtils.convertDpToPixel(100, itemView.context)

        (child.layoutParams as FrameLayout.LayoutParams).height =
            ((PresentationUtils.getScreenHeightInPx(itemView.context) * 1.5f).toInt())
        child.pivotY = PresentationUtils.convertDpToPixel(15, itemView.context) / (1 - scale)
        child.pivotX = PresentationUtils.getScreenWidthInPx(itemView.context) / 2f
        child.scaleX = scale
        child.scaleY = scale
    }
}