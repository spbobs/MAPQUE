package com.bobs.mapque.util

import android.annotation.SuppressLint
import android.content.Context
import com.bobs.baselibrary.util.loge
import com.bobs.mapque.BuildConfig
import com.bobs.mapque.R
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_native_ad.*
import java.util.concurrent.TimeUnit

object ADManager {
    private var isOpenAd: Boolean = false
    var nativeAd: NativeAd? = null
        get() {
            isOpenAd = true
            return field
        }
        set(value) {
            isOpenAd = false
            field = value
        }

    @SuppressLint("CheckResult")
    fun loadAd(context: Context) {
        val builder = AdLoader.Builder(context, BuildConfig.NATIVE_AD_ID )
            .forNativeAd {
                nativeAd = it
                loge("광고 로드 성공")
            }
//            .withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(errorCode: Int) {
//                    // 여기서 새 광고 로드는 권장 안함.
//                    loge("광고 로드 실패: $errorCode")
//                }
//
//                override fun onAdClicked() {
//                    loge("광고 클릭")
//                }
//
//                override fun onAdOpened() {
//                    loge("광고 열림")
//                }
//
//                override fun onAdClosed() {
//                    loge("광고 닫힘")
//                }
//            })

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()

        val adOptions = com.google.android.gms.ads.nativead.NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.build()
        adLoader.loadAd(AdRequest.Builder().build())

        // 1분 마다 한번씩 광고 변경
        Observable.timer(1, TimeUnit.MINUTES)
            .subscribeOn(Schedulers.io())
            .repeat()
            .subscribe {
                if(isOpenAd) adLoader.loadAd(AdRequest.Builder().build()) }
    }
}