package com.bobs.mapque.network.response.coord

import com.google.gson.annotations.SerializedName

data class DocumentsItem(
    @field:SerializedName("address")
    val address: Address? = null,

    @field:SerializedName("road_address")
    val roadAddress: RoadAddress? = null
) {
    fun convertDialogContent(): String {
        // 마커 터치시 뜨는 다이얼로그 내용으로 변환
        var message = "(구)주소 : ${address?.addressName}\n"
        if (roadAddress != null && !roadAddress.addressName.isNullOrEmpty())
            message += "도로명 주소: ${roadAddress.addressName}\n"

        val enableZipCode: String = if (address?.zipCode.isNullOrEmpty()) {
            if (!roadAddress?.zoneNo.isNullOrEmpty()) {
                roadAddress?.zoneNo!!
            } else {
                ""
            }
        } else address?.zipCode!!

        if (enableZipCode.isNotEmpty())
            message += "우편번호: $enableZipCode"

        return message
    }
}