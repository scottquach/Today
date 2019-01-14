package com.scottquach.today

import android.app.Application
import com.amitshekhar.DebugDB
import timber.log.Timber

class TodayApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        Timber.plant(DebugTree())
        Timber.d(DebugDB.getAddressLog())
    }

    companion object {
        private var instance: Application? = null
        fun getInstance() = instance
    }

    inner class DebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return String.format(
                "[L:%s] [M:%s] [C:%s]",
                element.lineNumber,
                element.methodName,
                super.createStackElementTag(element)
            )
        }
    }
}