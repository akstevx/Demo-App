package com.qucoon.demoapp.models

import com.google.gson.JsonArray
import java.io.Serializable

//data class GetCarsResponse (
//    val `data`:  List<Car>
//)
//
//

typealias GetCarsResponse = ArrayList<GetCarsResponseElement>

data class GetCarsResponseElement (
    val year: Long,
    val id: Long,
    val horsepower: Long,
    val make: String,
    val model: String,
    val price: Long,
    val img_url: String
):Serializable
