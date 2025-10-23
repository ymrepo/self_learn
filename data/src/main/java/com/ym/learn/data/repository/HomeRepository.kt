package com.ym.learn.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ym.learn.data.http.OkHttpSingleton
import com.ym.learn.data.http.OkHttpSingleton.BASE_URL
import com.ym.learn.data.model.BaseResponse
import com.ym.learn.data.model.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository {
    suspend fun getPeople(page: Int): BaseResponse<List<People>>? =
        withContext(Dispatchers.IO) {
            val client = OkHttpSingleton.client
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