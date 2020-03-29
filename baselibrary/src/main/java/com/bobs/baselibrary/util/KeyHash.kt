package com.bobs.baselibrary.util

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest

fun getKeyHash(activity: Activity): String{
    val info = activity.packageManager.getPackageInfo(
        activity.packageName,
        PackageManager.GET_SIGNATURES
    )
    for (sign in info.signatures) {
        val md = MessageDigest.getInstance("SHA")
        md.update(sign.toByteArray())
        val key = Base64.encodeToString(md.digest(), Base64.DEFAULT)

        return key
    }

    return ""
}