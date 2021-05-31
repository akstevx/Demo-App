package com.qucoon.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ncapdevi.fragnav.FragNavController
import com.qucoon.demoapp.base.BaseActivity
import com.qucoon.demoapp.base.BaseFragment
import com.qucoon.demoapp.views.HomeFragment
import com.qucoon.demoapp.views.SplashScreenFragment
import com.qucoon.demoapp.views.ViewDetailsFragment

class MainActivity : BaseActivity() {
    val fragments = lazy {
        listOf<BaseFragment>(SplashScreenFragment(), HomeFragment())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        initFragNavController(this,fragments.value, TAG,supportFragmentManager,R.id.content)
    }


    companion object {
        val TAG = "Main Activity"
        val SPLASH = FragNavController.TAB1
        val HOME = FragNavController.TAB2
        val DETAILS = FragNavController.TAB3
    }

}