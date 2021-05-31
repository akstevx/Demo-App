package com.qucoon.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.ncapdevi.fragnav.FragNavController
import com.qucoon.demoapp.base.BaseActivity
import com.qucoon.demoapp.base.BaseFragment
import com.qucoon.demoapp.views.HomeFragment
import com.qucoon.demoapp.views.SplashScreenFragment
import com.qucoon.demoapp.views.ViewDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*

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


    fun showLoading(b: Boolean) {
        if (b) {
            loadingRoot.visibility = View.VISIBLE
            loadingView.startRippleAnimation()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            loadingRoot.visibility = View.GONE
            loadingView.stopRippleAnimation()
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    companion object {
        val TAG = "Main Activity"
        val SPLASH = FragNavController.TAB1
        val HOME = FragNavController.TAB2
        val DETAILS = FragNavController.TAB3
    }

}