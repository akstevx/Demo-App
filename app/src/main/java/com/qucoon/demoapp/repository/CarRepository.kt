package com.qucoon.demoapp.repository

import com.qucoon.demoapp.base.BaseRepository
import com.qucoon.demoapp.models.GetCarsResponse
import com.qucoon.demoapp.network.NetworkAPI
import com.qucoon.demoapp.utils.UseCaseResult

interface CarRepository {
    suspend fun getCarList(): UseCaseResult<GetCarsResponse>
}

class CarRepositoryImpl(private val networkAPI: NetworkAPI): BaseRepository(), CarRepository{

    override suspend fun getCarList(): UseCaseResult<GetCarsResponse> {
        return safeGetApiCall( networkAPI::getCars, {it.isNotEmpty() }, { })
    }

}