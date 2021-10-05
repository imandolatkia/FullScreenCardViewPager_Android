package com.dolatkia.horizontallycards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dolatkia.horizontallycardslibrary.CardViewPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<CardViewPager>(R.id.cardViewPager).setAdapter(MyCartViewPagerAdapter(this), 0)
    }
}