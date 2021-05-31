package com.qucoon.demoapp.viewmodel

import com.qucoon.demoapp.base.BaseViewModel
import com.qucoon.demoapp.models.GetCarsResponse
import com.qucoon.demoapp.repository.CarRepository
import com.qucoon.demoapp.utils.SingleLiveEvent

class CarViewModel(private val carRepository: CarRepository): BaseViewModel(){
    val getCarsResponse = SingleLiveEvent<GetCarsResponse>()

    fun getCarList(){
        getApiRequest(carRepository::getCarList,getCarsResponse,{"Something went wrong"}, true)
    }
}
