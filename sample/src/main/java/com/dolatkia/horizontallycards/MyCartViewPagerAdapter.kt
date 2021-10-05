package com.dolatkia.horizontallycards

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.databinding.ItemActionbarBinding
import com.dolatkia.horizontallycards.databinding.RecyclerRowNameAndPriceBinding
import com.dolatkia.horizontallycards.databinding.RecyclerTopImageBinding
import com.dolatkia.horizontallycards.viewholders.ProductImageViewHolder
import com.dolatkia.horizontallycards.viewholders.HorizontalBoxesViewHolder
import com.dolatkia.horizontallycards.viewholders.GeneralViewHolder
import com.dolatkia.horizontallycards.viewholders.ProductNameAndPriceViewHolder
import com.dolatkia.horizontallycardslibrary.CartViewPagerAdapter

class MyCartViewPagerAdapter(private val activity: Activity) : CartViewPagerAdapter(activity) {

    private val itemTouchHelper = arrayListOf<Product>()

    init {
        addFakeItems()
    }

    override fun getCardRecyclerViewAdapter(position: Int): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return InnerRecyclerAdapter(activity, position, itemTouchHelper[position])
    }

    override fun getCardsCount(): Int {
        return itemTouchHelper.size
    }

    override fun onCreateActionBarCustomView(): View? {
        return ItemActionbarBinding.inflate(activity.layoutInflater).root
    }

    override fun onBindActionBarCustomView(position: Int, customView: View) {
        ItemActionbarBinding.bind(customView).title.text = "Title " + (position + 1).toString()
    }

    override fun onVerticalScrolled(
        recyclerView: RecyclerView,
        dy: Int,
        offset: Int,
        customActionBarView: View?
    ) {
    }

    override fun getOnCloseClickListener(position: Int, context: Context): View.OnClickListener {
        return View.OnClickListener { activity.onBackPressed() }
    }

    fun addFakeItems() {
        itemTouchHelper.add(Product("240$", R.drawable.img_1))
        itemTouchHelper.add(Product("220$", R.drawable.img_2))
        itemTouchHelper.add(Product("190$", R.drawable.img_3))
        itemTouchHelper.add(Product("200$", R.drawable.img_4))
        itemTouchHelper.add(Product("80$", R.drawable.img_5))
        itemTouchHelper.add(Product("280$", R.drawable.img_6))
        itemTouchHelper.add(Product("340$", R.drawable.img_7))
        itemTouchHelper.add(Product("140$", R.drawable.img_8))
        itemTouchHelper.add(Product("140$", R.drawable.img_9))
        itemTouchHelper.add(Product("148$", R.drawable.img_10))
        itemTouchHelper.add(Product("148$", R.drawable.img_11))
    }

    override fun loadData() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                addFakeItems()
                notifyDataSetChanged()
                dataLoaded()
            },
            1000 // value in milliseconds
        )
    }

    override fun hasMoreData(): Boolean {
        return true
    }
}


class InnerRecyclerAdapter(
    private val context: Context,
    private var cardPosition: Int,
    private var product: Product
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_ROW_IMAGE = 0
    val TYPE_ROW_TEXT = 1
    val TYPE_ROW_HORIZONTAL_BOXES = 2
    val TYPE_ROW_NAME_AND_PRICE = 3
    val TYPE_ADD_TO_CHART = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_ROW_IMAGE -> {
                return ProductImageViewHolder(
                    RecyclerTopImageBinding.inflate(LayoutInflater.from(context), parent, false)
                )
            }

            TYPE_ADD_TO_CHART -> return GeneralViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.recycler_row_add_to_chart, parent, false)
            )

            TYPE_ROW_TEXT -> return GeneralViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_row_text, parent, false)
            )

            TYPE_ROW_NAME_AND_PRICE -> return ProductNameAndPriceViewHolder(
                RecyclerRowNameAndPriceBinding.inflate(LayoutInflater.from(context), parent, false)
            )
        }
        return HorizontalBoxesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_row_boxes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductImageViewHolder) {
            holder.binding.image.setImageResource(product.imageRes)

        } else if (holder is ProductNameAndPriceViewHolder) {
            holder.binding.price.text = product.price
            holder.binding.title.text = "Beautiful Chair " + (cardPosition + 1).toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_ROW_IMAGE
        }

        when (position) {
            0 -> return TYPE_ROW_IMAGE
            1 -> return TYPE_ROW_NAME_AND_PRICE
            2 -> return TYPE_ADD_TO_CHART
            3 -> return TYPE_ROW_TEXT
            4 -> return TYPE_ROW_HORIZONTAL_BOXES
            5 -> return TYPE_ROW_TEXT
            6 -> return TYPE_ROW_IMAGE
            7 -> return TYPE_ADD_TO_CHART
        }
        return TYPE_ROW_HORIZONTAL_BOXES
    }
}


