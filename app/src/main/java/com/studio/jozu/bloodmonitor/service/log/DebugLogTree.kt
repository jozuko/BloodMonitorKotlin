package com.studio.jozu.bloodmonitor.service.log

import android.util.Log
import timber.log.Timber

class DebugLogTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        when (priority) {
            Log.VERBOSE -> Log.v(tag, message, t)
            Log.DEBUG -> Log.d(tag, message, t)
            Log.INFO -> Log.i(tag, message, t)
            Log.WARN -> Log.w(tag, message, t)
            Log.ERROR -> Log.e(tag, message, t)
            Log.ASSERT -> Log.println(priority, tag, message)
            else -> return
        }
    }
}