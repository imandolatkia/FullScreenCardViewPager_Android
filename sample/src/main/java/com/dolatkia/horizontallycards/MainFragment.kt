package com.dolatkia.horizontallycards

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dolatkia.horizontallycards.databinding.ActivityMainBinding
import com.dolatkia.horizontallycards.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MyFullScreenCardViewPagerAdapter
    private val isRtl = false
    private lateinit var myViewPagerListener: MyViewPagerListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MyFullScreenCardViewPagerAdapter(activity as Activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // create layout
        binding = FragmentMainBinding.inflate(inflater)

        // set rtl
        if (isRtl) binding.fullScreenCardViewPager.setRTL()

        // set customize adapter to fullScreenCardViewPager
        // 0 = start position
        // adapter = your customize adapter (for more details see sample app)
        binding.fullScreenCardViewPager.setAdapter(adapter, 0)

        //create listener
        myViewPagerListener = MyViewPagerListener(activity as Activity)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.addListener(myViewPagerListener)
    }

    override fun onPause() {
        super.onPause()
        adapter.removeListener(myViewPagerListener)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.fullScreenCardViewPager.onConfigurationChanged(newConfig)
    }
}