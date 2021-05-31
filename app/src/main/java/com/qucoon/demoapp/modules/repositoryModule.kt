package com.qucoon.demoapp.modules

import com.qucoon.demoapp.repository.CarRepository
import com.qucoon.demoapp.repository.CarRepositoryImpl
import org.koin.dsl.module

//repository module dependency injection
val repositoryModule = module {
        single <CarRepository>{ CarRepositoryImpl( networkAPI = get()) }
}