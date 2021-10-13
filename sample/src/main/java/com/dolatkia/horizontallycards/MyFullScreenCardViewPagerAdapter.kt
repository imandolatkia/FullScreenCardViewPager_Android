package com.dolatkia.horizontallycards

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycards.databinding.ItemActionbarBinding
import com.dolatkia.horizontallycards.innerRecycler.InnerRecyclerAdapter
import com.dolatkia.horizontallycards.model.Product
import com.dolatkia.horizontallycardslibrary.FullScreenCardViewPagerAdapter
import com.dolatkia.horizontallycardslibrary.PresentationUtils


/*******
 * see BaseFullScreenCardViewPagerAdapter for more features and documentation
 */

class MyFullScreenCardViewPagerAdapter(private val activity: Activity) :
    FullScreenCardViewPagerAdapter(activity) {

    private val productsList = arrayListOf<Product>()

    init {
        // add fake products to productsList
        // you can pass products list to this adapter
        addFakeItems()
    }

    /*********** critical methods ************/
    /** see BaseFullScreenCardViewPagerAdapter for more features and documentation ***/

    // you should create your own RecyclerView.Adapter<RecyclerView.ViewHolder>
    // data in this adapter will save
    override fun getCardRecyclerViewAdapter(position: Int): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return InnerRecyclerAdapter(activity, position, productsList[position])
    }

    // return number of cards (except loading card, loading card will add with library)
    override fun getCardsCount(): Int {
        return productsList.size
    }

    // return View.OnClickListener to call when close button clicked
    override fun getOnCloseClickListener(position: Int, context: Context): View.OnClickListener {
        return View.OnClickListener { activity.onBackPressed() }
    }

    /*********** actionbar methods (Optional) ************/
    override fun onCreateActionBarCustomView(): View {
        return ItemActionbarBinding.inflate(activity.layoutInflater).root
    }

    // update actionbar data with correct data
    override fun onBindActionBarCustomView(position: Int, customView: View) {
        ("Beautiful Chair " + (position + 1).toString()).also {
            ItemActionbarBinding.bind(customView).title.text = it
        }
    }

    /*********** endless cards methods (Optional) ************/
    /** see BaseFullScreenCardViewPagerAdapter for more features and documentation ***/

    // return true if you have endless cards
    // you can ignore it if you don't have endless cards
    override fun hasMoreData(): Boolean {

        return true
    }

    // load data (get from net or db)
    // you can ignore it if you don't have endless list
    override fun loadData() {

        Handler(Looper.getMainLooper()).postDelayed(
            {
                addFakeItems()

                // call this method when new data is ready
                dataLoaded()
            },
            1000 // value in milliseconds
        )
    }

    /*********** UI customization methods (Optional) ************/
    /** see BaseFullScreenCardViewPagerAdapter for more features and documentation ***/

    // customize distance from top to enter actionbar
    override fun getActionBarStartAnimationOffsetThreshold(
        recyclerView: RecyclerView,
        customActionBarView: View?
    ): Int {
        return PresentationUtils.convertDpToPixel(150, recyclerView.context)
    }

    /*********** my private methods :) (Optional) ************/
    /** see BaseFullScreenCardViewPagerAdapter for more features and documentation ***/

    private fun addFakeItems() {
        // :)
        productsList.add(Product("240$", R.drawable.img_1))
        productsList.add(Product("220$", R.drawable.img_2))
        productsList.add(Product("190$", R.drawable.img_3))
        productsList.add(Product("200$", R.drawable.img_4))
        productsList.add(Product("80$", R.drawable.img_5))
        productsList.add(Product("280$", R.drawable.img_6))
        productsList.add(Product("340$", R.drawable.img_7))
        productsList.add(Product("140$", R.drawable.img_8))
        productsList.add(Product("140$", R.drawable.img_9))
        productsList.add(Product("148$", R.drawable.img_10))
        productsList.add(Product("148$", R.drawable.img_11))
    }
}


