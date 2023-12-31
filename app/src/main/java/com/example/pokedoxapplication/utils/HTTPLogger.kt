package com.example.pokedoxapplication.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object HTTPLogger {
    fun getLogger(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}