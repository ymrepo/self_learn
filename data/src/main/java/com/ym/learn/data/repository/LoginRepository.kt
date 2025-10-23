package com.ym.learn.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ym.learn.data.http.OkHttpSingleton
import com.ym.learn.data.http.OkHttpSingleton.BASE_URL
import com.ym.learn.data.model.BaseResponse
import com.ym.learn.data.model.People
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient


class LoginRepository @Inject constructor(val client: OkHttpClient) {
    suspend fun login(phone: String): BaseResponse<People>? =
        withContext(Dispatchers.IO) {
            val request = okhttp3.Request.Builder().url("${BASE_URL}people/1/").build()
            val call = client.newCall(request)
            runCatching { call.execute() }.onSuccess { action ->
                val body = action.body?.string()
                val type = object : TypeToken<BaseResponse<People>>() {}.type
                return@withContext Gson().fromJson(body, type)
            }.onFailure { action ->
                return@withContext null
            }
            return@withContext null
        }
}