package com.bobs.mapque.util.ext

import android.Manifest
import android.content.Context
import com.bobs.mapque.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

fun checkPermission(context: Context,  permissionListener: PermissionListener, vararg permissions: String) {
    TedPermission.with(context)
        .setPermissionListener(permissionListener)
        .setRationaleTitle(R.string.permission_rationale_title)
        .setRationaleMessage(R.string.permission_rationale_msg)
        .setDeniedTitle(R.string.permission_denied_title)
        .setDeniedMessage(R.string.permission_denied_msg)
        .setPermissions(*permissions)
        .check()
}
fun checkLocationPermission(context: Context,  permissionListener: PermissionListener) {
    TedPermission.with(context)
        .setPermissionListener(permissionListener)
        .setRationaleTitle(R.string.permission_rationale_title)
        .setRationaleMessage(R.string.permission_rationale_msg)
        .setDeniedTitle(R.string.permission_denied_title)
        .setDeniedMessage(R.string.permission_denied_msg)
        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        .check()
}