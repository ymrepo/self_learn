package com.ym.learn.data.http

import okhttp3.OkHttpClient

object OkHttpSingleton {
    const val BASE_URL="https://swapi.dev/api/"
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }
}
