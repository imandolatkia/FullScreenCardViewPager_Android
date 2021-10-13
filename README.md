# FullScreenCardViewPager for Android
Endless full-screen card ViewPager inspired by apple iBook for Android

</br></b>

# Features
* Scale cards on scroll up.
* Endless (from server or database).
* Show loading card.
* Floating actionbar.
* Lock horizontal scroll after card expanded.
* Push side cards on card scale.
* Support **java** and **kotlin** projects.
* Easy to use (3 tiny steps).
* Support API > 16.

</br></b>

# How to install? [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.dolatkia/full-screen-card-viewpager/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.dolatkia/full-screen-card-viewpager)

Add the following line to the **app-level** build.gradle file, in dependencies scope:
```Gradle
dependencies {
    ...
    // add this line:
    implementation "com.dolatkia:full-screen-card-viewpager:1.0.0"
}
```

</br></b>

# How to use it?
**Step 1:** Create ```FullScreenCardViewPager``` in your Activity/Fragment. you can create it with java/kotlin code or use it in layout xml file:
```xml
    <com.dolatkia.horizontallycardslibrary.FullScreenCardViewPager
        android:id="@+id/fullScreenCardViewPager"
        android:background="@color/cards_background"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
</br></b>
**Step 2:** Create adapter class that extends from```FullScreenCardViewPagerAdapter``` and override 3 abstract methods:
```kotlin
class MyFullScreenCardViewPagerAdapter(private val activity: Activity) :
    FullScreenCardViewPagerAdapter(activity) {
    
    // list of products that you should fill it yourself
    private val productsList = arrayListOf<Product>()

    // you should create your own RecyclerView.Adapter<RecyclerView.ViewHolder> for each card with the given position
    //data in this adapter will save
    override fun getCardRecyclerViewAdapter(position: Int): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return InnerRecyclerAdapter(activity, position, productsList[position])
    }

    // return number of cards (except loading card, loading card will add with the library)
    override fun getCardsCount(): Int {
        return productsList.size
    }

    // return View.OnClickListener to call when close button clicked
    override fun getOnCloseClickListener(position: Int, context: Context): View.OnClickListener {
        return View.OnClickListener { activity.onBackPressed() }
    }    
}

```
```productsList``` = list of products that you should fill it yourself, each product is for one card.</br>```InnerRecyclerAdapter``` =  is your custom  RecyclerView.Adapter to display in each card. for more details see sample app.
</br></b>

**Step 3:** set adapter created in step 2 for FullScreenCardViewPager created in step 1. all done :)
```kotlin
// set customize adapter to fullScreenCardViewPager
// 0 = start position
// adapter = your customize adapter (for more details see sample app)
adapter = MyFullScreenCardViewPagerAdapter(this)
binding.fullScreenCardViewPager.setAdapter(adapter, 0)
```

</br></b>

# Some other settings and customization:
## Action bar:
If you need actionbars for cards override these two methods in your adapter:
```kotlin
// create and return actionbar view
override fun onCreateActionBarCustomView(): View {
  return ItemActionbarBinding.inflate(activity.layoutInflater).root
}

// update actionbar view with relevant data
override fun onBindActionBarCustomView(position: Int, customView: View) {
  ("Beautiful Chair " + (position + 1).toString()).also {
    ItemActionbarBinding.bind(customView).title.text = it
  }
}
```
</br></b>
## Endless cards: 
override these two methods in your adapter:
```kotlin

override fun hasMoreData(): Boolean {
  return true
  // return true if you have endless cards and your data is incomplete,
  // return false if you don't have endless cards or you get all data or 
}

// load data (from server or db) in this method and add it to the adapter
// you should  manage your load data sequence yourself
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
```
</br></b>
## Customize UI:
To customize UI override these methods in your adapter:

```kotlin
// customize distance from top to enter actionbar
open fun getActionBarStartAnimationOffsetThreshold(
  recyclerView: RecyclerView,
  customActionBarView: View?
  ): Int {
  return PresentationUtils.convertDpToPixel(50, recyclerView.context)
}

// customize cards background-color
open fun getCardsColor(position: Int, context: Context): Int {
  return Color.parseColor("#ffffff")
}

// customize cards top-radius 
open fun getCardRadius(context: Context): Int {
  return PresentationUtils.convertDpToPixel(15, context)
}

// customize close icon
open fun getCloseResId(position: Int, context: Context): Int {
  return R.drawable.ic_close
}

// customize close color
open fun getCloseColor(position: Int, context: Context): Int {
  return Color.parseColor("#444444")
}
```



