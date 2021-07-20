package com.dolatkia.horizontallycards.viewholders

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.R
import com.google.android.material.card.MaterialCardView
import java.util.*

class BoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val box: MaterialCardView = itemView.findViewById(R.id.card)
    public val num: TextView? = itemView.findViewById(R.id.num)
    val mRandom: Random = Random(System.currentTimeMillis())
    var colors: Array<String> = arrayOf(
        "#f6e58d",
        "#f9ca24",
        "#7ed6df",
        "#22a6b3",
        "#ffbe76",
        "#f0932b",
        "#e056fd",
        "#be2edd",
        "#ff7979",
        "#eb4d4b",
        "#686de0",
        "#4834d4",
        "#badc58",
        "#6ab04c",
        "#30336b",
        "#130f40",
        "#dff9fb",
        "#c7ecee",
        "#95afc0",
        "#535c68"
    )
    var colors2: Array<String> = arrayOf(
        "#55efc4",
        "#00b894",
        "#ffeaa7",
        "#fdcb6e",
        "#81ecec",
        "#00cec9",
        "#fab1a0",
        "#74b9ff",
        "#0984e3",
        "#ff7675",
        "#a29bfe",
        "#6c5ce7",
        "#fd79a8",
        "#e84393",
        "#30336b",
        "#dff9fb",
        "#c7ecee",
        "#95afc0",
        "#b8e994",
        "#78e08f",
        "#38ada9",
        "#34e7e4",
        "#00d8d6",
        "#0fbcf9",
        "#ffdd59",
        "#ffd32a"
    )

    fun setRandomColor() {
        box.setCardBackgroundColor(Color.parseColor(colors2[mRandom.nextInt(25)]))
    }

    fun generateRandomColor(): Int {
        // This is the base color which will be mixed with the generated one
//        val baseColor: Int = Color.parseColor("#bbf7f7")
        val baseColor: Int = Color.parseColor("#aad3fd")
        val baseRed: Int = Color.red(baseColor)
        val baseGreen: Int = Color.green(baseColor)
        val baseBlue: Int = Color.blue(baseColor)
        val red: Int = (baseRed + mRandom.nextInt(256)) / 2
        val green: Int = (baseGreen + mRandom.nextInt(256)) / 2
        val blue: Int = (baseBlue + mRandom.nextInt(256)) / 2
        return Color.rgb(red, green, blue)
    }
}