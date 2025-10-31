package com.ym.learn.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ym.learn.data.http.OkHttpSingleton.BASE_URL
import com.ym.learn.data.model.BaseResponse
import com.ym.learn.data.model.People
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class HomeRepository @Inject constructor(val client: OkHttpClient) {
    suspend fun getPeople(page: Int): BaseResponse<List<People>>? =
        withContext(Dispatchers.IO) {
            val request = okhttp3.Request.Builder().url("${BASE_URL}planets/?page=$page").build()
            val call = client.newCall(request)
            runCatching {
                call.execute()
            }.onSuccess { result ->
                val body = result.body?.string()
                val type = object : TypeToken<BaseResponse<List<People>>>() {}.type
                val base: BaseResponse<List<People>> = Gson().fromJson(body, type)
                return@withContext base
            }.onFailure {throwable->
                return@withContext null
            }
            return@withContext null
        }
}