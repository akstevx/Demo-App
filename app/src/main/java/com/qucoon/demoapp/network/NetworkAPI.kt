package com.qucoon.demoapp.network

import com.qucoon.demoapp.models.GetCarsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NetworkAPI {

    @GET("cars")
    fun getCars(): Deferred<GetCarsResponse>
}