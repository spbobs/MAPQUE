package com.bobs.mapque.network.response.searchaddress

import com.google.gson.annotations.SerializedName

data class DocumentsItem(
    @field:SerializedName("address")
	val address: Address? = null,

    @field:SerializedName("address_type")
	val addressType: String? = null,

    @field:SerializedName("x")
	val X: String? = null,

    @field:SerializedName("y")
	val Y: String? = null,

    @field:SerializedName("address_name")
	val addressName: String? = null,

    @field:SerializedName("road_address")
	val roadAddress: RoadAddress? = null
)