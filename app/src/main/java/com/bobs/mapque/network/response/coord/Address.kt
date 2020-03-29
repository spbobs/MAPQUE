package com.bobs.mapque.network.response.coord

import com.google.gson.annotations.SerializedName

data class Address(
	@field:SerializedName("mountain_yn")
	val mountainYn: String? = null,

	@field:SerializedName("region_3depth_name")
	val region3depthName: String? = null,

	@field:SerializedName("main_address_no")
	val mainAddressNo: String? = null,

	@field:SerializedName("sub_address_no")
	val subAddressNo: String? = null,

	@field:SerializedName("address_name")
	val addressName: String? = null,

	@field:SerializedName("region_2depth_name")
	val region2depthName: String? = null,

	@field:SerializedName("region_1depth_name")
	val region1depthName: String? = null,

	@field:SerializedName("zip_code")
	val zipCode: String? = null
)