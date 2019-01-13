package com.scottquach.today

import android.app.Application

class TodayApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
    }

    companion object {
        private var instance: Application? = null
        fun getInstance() = instance
    }
}