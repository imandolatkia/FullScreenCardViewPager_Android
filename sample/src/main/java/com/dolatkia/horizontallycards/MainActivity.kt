package com.dolatkia.horizontallycards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.horizontallycardslibrary.CardViewPager
import com.dolatkia.horizontallycardslibrary.CartViewPagerAdapter
import com.dolatkia.horizontallycardslibrary.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<CardViewPager>(R.id.cardViewPager).setAdapter(MyAdapter(this))
    }
}