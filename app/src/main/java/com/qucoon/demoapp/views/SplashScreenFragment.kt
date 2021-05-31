package com.qucoon.demoapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qucoon.demoapp.MainActivity
import com.qucoon.demoapp.R
import com.qucoon.demoapp.base.BaseFragment
import com.qucoon.demoapp.utils.delayFor
import com.qucoon.demoapp.viewmodel.CarViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashScreenFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        delayFor(3000){
            mFragmentNavigation.switchFragment(MainActivity.HOME)
        }
    }

}