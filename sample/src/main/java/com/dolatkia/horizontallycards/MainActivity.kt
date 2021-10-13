package com.dolatkia.horizontallycards

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.dolatkia.horizontallycards.databinding.ActivityMainBinding
import com.dolatkia.horizontallycardslibrary.PresentationUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyFullScreenCardViewPagerAdapter
    private val isRtl = false
    private lateinit var myViewPagerListener: MyViewPagerListener

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
        adapter = MyFullScreenCardViewPagerAdapter(this)
        binding.fullScreenCardViewPager.setAdapter(adapter, 0)

        //create listener
        myViewPagerListener = MyViewPagerListener(this)

    }

    override fun onResume() {
        super.onResume()
        adapter.addListener(myViewPagerListener)
    }

    override fun onPause() {
        super.onPause()
        adapter.removeListener(myViewPagerListener)
    }
}