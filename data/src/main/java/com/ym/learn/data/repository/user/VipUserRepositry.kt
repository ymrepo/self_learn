package com.ym.learn.data.repository.user

import android.util.Log
import jakarta.inject.Inject

class VipUserRepository @Inject constructor(): IUserRepository {
    override fun getUser() {
    }

    override fun saveUser() {
        Log.i("UserRepo", "vip saveUser...")
    }
}