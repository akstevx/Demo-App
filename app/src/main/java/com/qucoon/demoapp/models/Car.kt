package com.qucoon.demoapp.models

import java.io.Serializable

data class Car(
    val year:Long,
    val id:Long,
    val horsepower:Long,
    val make:String,
    val model:String,
    val price:Double,
    val img_url:String
): Serializable