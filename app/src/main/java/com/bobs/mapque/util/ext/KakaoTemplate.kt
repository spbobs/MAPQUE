package com.bobs.mapque.util.ext

import android.content.Context
import com.bobs.baselibrary.util.loge
import com.bobs.mapque.BuildConfig
import com.bobs.mapque.R
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.*
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback

//fun sendLocatoinTemplate(
//    context: Context,
//    addressName: String,
//    latitude: Double,
//    longitude: Double
//) {
//    val params = LocationTemplate.newBuilder(
//        addressName,
//        ContentObject.newBuilder(
//            addressName, "",
//            LinkObject.newBuilder()
//                .setMobileWebUrl("https://map.kakao.com/link/map/${latitude},${longitude}")
//                .setWebUrl("https://map.kakao.com/link/map/${latitude},${longitude}")
//                .build()
//        ).setDescrption("검색 위치 공유합니다.").build()
//    ).setAddressTitle(addressName).build()
//
//    KakaoLinkService.getInstance().sendDefault(
//        context,
//        params,
//        object : ResponseCallback<KakaoLinkResponse>() {
//            override fun onSuccess(result: KakaoLinkResponse?) {
//                loge("카카오톡 공유 성공")
//            }
//
//            override fun onFailure(errorResult: ErrorResult?) {
//                loge("카카오톡 공유 실패: ${errorResult?.errorCode} - ${errorResult?.errorMessage}")
//            }
//        })
//}
//
//fun sendFeedTemplate(context: Context, addressName: String, latitude: Double, longitude: Double) {
//    val params = FeedTemplate.newBuilder(
//        ContentObject.newBuilder(
//            addressName,
//            "",
//            LinkObject.newBuilder().setMobileWebUrl("https://map.kakao.com/link/map/${latitude},${longitude}")
//                .setWebUrl("https://map.kakao.com/link/map/${latitude},${longitude}").build()
//        ).setDescrption("검색 위치 공유합니다.")
//            .build()
//    )
//        .addButton(
//            ButtonObject(
//                "위치 보기", LinkObject.newBuilder()
//                    .setWebUrl("https://map.kakao.com/link/map/${latitude},${longitude}")
//                    .setMobileWebUrl("https://map.kakao.com/link/map/${latitude},${longitude}")
//                    .build()
//            )
//        )
//        .build()
//
//    KakaoLinkService.getInstance().sendDefault(context, params, object:ResponseCallback<KakaoLinkResponse>(){
//        override fun onSuccess(result: KakaoLinkResponse?) {
//            loge("카카오톡 공유 성공")
//        }
//
//        override fun onFailure(errorResult: ErrorResult?) {
//            loge("카카오톡 공유 실패: ${errorResult?.errorCode} - ${errorResult?.errorMessage}")
//        }
//    })
//}

fun sendCustomTemplate(context: Context, addressName: String, latitude: Double, longitude: Double) {
    val templateArgs = mutableMapOf<String, String>()
    templateArgs["\${title}"] = addressName
    templateArgs["\${desc}"] = context.getString(R.string.template_content)
    templateArgs["\${latitude}"] = latitude.toString()
    templateArgs["\${longitude}"] = longitude.toString()

    KakaoLinkService.getInstance()
        .sendCustom(context, BuildConfig.KAKAO_SDK_CUSTOMTEMPLETE_ID, templateArgs, object : ResponseCallback<KakaoLinkResponse>() {
            override fun onSuccess(result: KakaoLinkResponse?) {
                loge("카카오톡 공유 성공")
            }

            override fun onFailure(errorResult: ErrorResult?) {
                loge("카카오톡 공유 실패: ${errorResult?.errorCode} - ${errorResult?.errorMessage}")
            }
        })
}