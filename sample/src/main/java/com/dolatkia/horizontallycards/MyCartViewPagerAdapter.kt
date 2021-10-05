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
import com.dolatkia.horizontallycardslibrary.PresentationUtils

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
        ItemActionbarBinding.bind(customView).title.text = "Beautiful Chair " + (position + 1).toString()
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

    override fun getActionBarStartAnimationOffsetThreshold(
        recyclerView: RecyclerView,
        customActionBarView: View?
    ): Int {
        return PresentationUtils.convertDpToPixel(150, recyclerView.context)
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
}


