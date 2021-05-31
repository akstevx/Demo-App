package com.qucoon.demoapp.utils;


sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class ActivateProfile<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
    class Failed(val errorMessage:String): UseCaseResult<Nothing>()
    class SessionTimeOut(val errorMessage:String): UseCaseResult<Nothing>()
    class FailedAPI<out T : Any>(val data: T): UseCaseResult<T>()
}