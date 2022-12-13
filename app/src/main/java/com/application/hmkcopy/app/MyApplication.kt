package com.application.hmkcopy.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    companion object {
        lateinit var instance: MyApplication
    }
    override fun onCreate() {
        instance = this
    }

}