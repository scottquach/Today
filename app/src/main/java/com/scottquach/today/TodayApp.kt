package com.scottquach.today

import android.app.Application
import com.amitshekhar.DebugDB
import com.scottquach.today.util.PrefUtil
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

val prefUtil: PrefUtil by lazy {
    TodayApp.prefs!!
}

class TodayApp: Application() {

    companion object {
        private var instance: Application? = null
        var prefs: PrefUtil? = null

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        JodaTimeAndroid.init(this)
        prefs = PrefUtil(this)
        Timber.plant(DebugTree())
        Timber.d(DebugDB.getAddressLog())
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