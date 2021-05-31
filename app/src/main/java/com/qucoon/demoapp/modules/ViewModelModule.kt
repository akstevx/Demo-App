package com.qucoon.demoapp.modules

import com.qucoon.demoapp.viewmodel.CarViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

//view model module dependency injection
val viewModelModule = module{
    viewModel{ CarViewModel(carRepository = get()) }
}
