package com.dolatkia.horizontallycards

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.dolatkia.horizontallycards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val isRtl = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create layout
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // set rtl
        if (isRtl) binding.fullScreenCardViewPager.setRTL()

        // set customize adapter to fullScreenCardViewPager
        // 0 = start position
        // MyFullScreenCardViewPagerAdapter = my customize adapter
        binding.fullScreenCardViewPager.setAdapter(MyFullScreenCardViewPagerAdapter(this), 0)
    }
}