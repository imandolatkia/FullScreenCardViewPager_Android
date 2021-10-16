package com.dolatkia.horizontallycards

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.dolatkia.horizontallycards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set root view
        setContentView(R.layout.activity_main)

        // open fragment
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentContainer, MainFragment())
            transaction.commit()
        }
    }
}