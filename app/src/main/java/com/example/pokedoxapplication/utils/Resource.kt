package com.example.pokedoxapplication.utils

/**
 * Resource class is responsible for parsing the responses coming from the API
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(data: T? =null): Resource<T>(data)

}