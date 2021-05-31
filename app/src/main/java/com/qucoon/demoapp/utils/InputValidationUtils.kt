package com.qucoon.demoapp.utils;

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object InputValidationUtils {

    fun handleException(exception: Throwable):String {
        println(exception)
        return  when (exception) {
            is SocketTimeoutException -> "Request time out. Try again"
            is UnknownHostException -> "Check your internet connection"
            is HttpException -> "Check your internet connection"
            is ConnectException -> "Check your internet connection"
            is HttpException -> "reload"
            is retrofit2.HttpException -> "reload"
            else -> "Something went wrong"

        }
    }


    fun validateInput(vararg arguments: Pair<MutableLiveData<String>, String>): Pair<Boolean,String?> {
        return  runBlocking {
            async (Dispatchers.Default){
                var error:String? = null
                var isValid = true
                arguments.forEach {
                        pair ->
                    val currentString:String? = pair.first.value
                    currentString?.let {
                    } ?: run {
                        isValid = false
                        if(error == null){ error = "${pair.second} is missing"}else{error += ", ${pair.second} is missing"}
                    }
                }
                Pair(isValid, error)
            }.await()

        }




    }
}
fun Exception.handleException():String{
    return InputValidationUtils.handleException(this)
}
fun Throwable.handleException():String{
    return InputValidationUtils.handleException(this)
}