package com.bobs.mapque.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.bobs.baselibrary.ext.toast
import com.bobs.mapque.ui.MainActivity
import com.bobs.mapque.util.ext.checkPermission
import com.gun0912.tedpermission.PermissionListener

@SuppressLint("MissingPermission")
class GpsTracker(private val activity: MainActivity) : LocationListener {

    private val locationManager: LocationManager by lazy {
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private var location: Location? = null

    private val MIN_DISTANCE_UPDATES: Float = 10F
    private val MIN_TIME_UPDATES: Long = 1000 * 60

    fun isEnableGetLocation(out: () -> Unit) {
        // provider들 가능 여부 체크
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            activity.showDialogGpsSetting()
        } else {
            checkPermission(
                activity,
                object : PermissionListener {
                    override fun onPermissionGranted() {
                        out()
                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                        activity.toast(deniedPermissions!![0])
                        activity.finish()
                    }
                },
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    fun getCurrentLocation(): Location {
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGpsEnabled || isNetworkEnabled) {
            location = getLastKnownLocation()
        }

        return location!!
    }

    fun getLastKnownLocation(): Location? {
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers.toList()) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue

            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }

        return bestLocation
    }

    override fun onLocationChanged(p0: Location?) {
        if (location == null) {
            location = Location(p0)
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
        activity.toast("위치 서비스 이용 가능")
    }

    override fun onProviderDisabled(p0: String?) {
        activity.toast("위치 서비스 이용 불가")
    }

    fun checkLocationServicesStatus(): Boolean =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
}