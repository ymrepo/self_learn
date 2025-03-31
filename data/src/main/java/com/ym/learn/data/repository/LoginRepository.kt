package com.ym.learn.data.repository

import com.ym.learn.data.http.OkHttpSingleton
import com.ym.learn.data.http.OkHttpSingleton.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoginRepository {
    //        return Gson().fromJson(result.body?.string(), LoginResult::class.java)
    suspend fun login(phone: String): String =
        withContext(Dispatchers.IO) {
            val client = OkHttpSingleton.client
            val request = okhttp3.Request.Builder().url("${BASE_URL}people/1/").build()
            val call = client.newCall(request)
            val result = call.execute()
            return@withContext result.body?.string() ?: ""
        }
}