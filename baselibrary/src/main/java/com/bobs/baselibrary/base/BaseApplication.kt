package com.bobs.baselibrary.base

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

open class BaseApplication : Application() {
    fun isDebuggable(): Boolean {
        var debuggable = false

        val pm = applicationContext.packageManager
        try {
            val appinfo = pm.getApplicationInfo(applicationContext.packageName, 0)
            debuggable = (0 != (appinfo.flags and ApplicationInfo.FLAG_DEBUGGABLE))
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
        }

        return debuggable
    }
}

