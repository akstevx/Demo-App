package com.qucoon.demoapp.base

import androidx.lifecycle.*
import com.qucoon.demoapp.utils.SingleLiveEvent
import com.qucoon.demoapp.utils.UseCaseResult
import com.qucoon.demoapp.utils.handleException
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel:ViewModel(), CoroutineScope,LifecycleObserver {
    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val showSessionTimeOut= SingleLiveEvent<String>()
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

    fun validateInput(vararg data:Pair<String,String>):Boolean{
        var error = ""
        data.forEach { field ->
            if(field.first.isNullOrEmpty()){
                if(error == "") error += "${field.second} is missing" else  ", ${field.second} is missing"
            }
        }
        if(error != ""){
            showError.value = error
            return false
        }
        return true
    }

    //         apiRequest(request, selfServiceHistoryRepository::getSelfServiceHistory, selfServiceHistoryResponse, {it.responsemessage ?: "Failed"})

    fun <R:Any,T:Any> apiRequest(
        request:R, apiCall:suspend (request:R)-> UseCaseResult<T>, observer:SingleLiveEvent<T>, getError: (response: T) -> String,
        displayLoading:Boolean = true, showErrorMessage:Boolean = true,
        onErrorObserver: SingleLiveEvent<Unit>? = null){
        if(displayLoading)  showLoading.value = true
        launch {
            val response = withContext(IO){apiCall(request)}
            if(displayLoading)   showLoading.value = false
            when(response){
                is UseCaseResult.Success -> observer.value = response.data
                is UseCaseResult.FailedAPI -> {

                    if(showErrorMessage) showError.value = getError(response.data)
                    onErrorObserver?.call()
                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.handleException()
                    onErrorObserver?.call()
                }
            }
        }
    }

    fun <T:Any> getApiRequest( apiCall:suspend ()-> UseCaseResult<T>, observer:SingleLiveEvent<T>, getError: (response: T) -> String,
            displayLoading:Boolean = true,showErrorMessage:Boolean = true,
            onErrorObserver:SingleLiveEvent<Unit>? = null){
        if(displayLoading)  showLoading.value = true
        launch {
            val response = withContext(IO){apiCall()}
            if(displayLoading)   showLoading.value = false
            when(response){
                is UseCaseResult.Success -> observer.value = response.data
                is UseCaseResult.FailedAPI -> {

                    if(showErrorMessage) showError.value = getError(response.data)
                    onErrorObserver?.call()
                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.handleException()
                    onErrorObserver?.call()
                }
            }
        }
    }


    fun <R:Any,T:Any> apiRequest(request:R, apiCall:suspend (request:R)-> UseCaseResult<T>, observer:SingleLiveEvent<T>, getError:(response:T) -> String,
                                 onSuccessOperations:(response:T) -> Unit,displayLoading:Boolean = true,showErrorMessage:Boolean = true,onErrorObserver:SingleLiveEvent<Unit>? = null,onErrorAction: ((response:T?) -> Unit)? = null
    ){
       if(displayLoading) showLoading.value = true
        launch {
            val response = withContext(IO){apiCall(request)}
            if(displayLoading)   showLoading.value = false
            when(response){
                is UseCaseResult.Success -> {
                    onSuccessOperations(response.data)
                    observer.value = response.data
                }
                is UseCaseResult.FailedAPI -> {
                    if(showErrorMessage) showError.value = getError(response.data)
                    onErrorObserver?.call()
                    onErrorAction?.let { it(response.data) }
                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.handleException()
                    onErrorObserver?.call()
                    onErrorAction?.let { it(null) }
                }
            }
        }
    }
}
fun <T> SingleLiveEvent<T>.observeUnit(owner: LifecycleOwner,action:(T?)->Unit){
    this.observe(owner, Observer { action(it) })
}
fun <T> SingleLiveEvent<T>.observeChange(owner: LifecycleOwner,action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}
fun <T> LiveData<T>.observeChange(owner: LifecycleOwner,action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}
fun <T> MutableLiveData<T>.observeChange(owner: LifecycleOwner,action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}