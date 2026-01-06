//package com.creative.qrcodescanner
//
//import android.app.Application
//import android.os.Build
//import android.os.StrictMode
//import androidx.annotation.RequiresApi
//import dagger.hilt.android.HiltAndroidApp
//
///**
// * Created by dan on 11/01/2024
// *
// * Copyright © 2024 1010 Creative. All rights reserved.
// */
//
//@HiltAndroidApp
//class App : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//            setupStrictMode()
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
//    private fun setupStrictMode() {
//        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
//            .detectAll()
//            .penaltyLog()
//            .penaltyDialog()
//            .build())
//    }
//}