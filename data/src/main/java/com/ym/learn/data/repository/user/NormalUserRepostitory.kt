package com.ym.learn.data.repository.user

import android.util.Log
import jakarta.inject.Inject

class NormalUserRepository @Inject constructor() : IUserRepository {
    override fun getUser() {

    }

    override fun saveUser() {
        Log.i("UserRepo", "normal saveUser...")
    }
}