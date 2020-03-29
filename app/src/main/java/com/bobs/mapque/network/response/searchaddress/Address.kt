package com.bobs.mapque.network.response.searchaddress

import com.google.gson.annotations.SerializedName

data class Address(
	@field:SerializedName("h_code")
	val hCode: String? = null,

	@field:SerializedName("region_3depth_name")
	val region3depthName: String? = null,

	@field:SerializedName("main_address_no")
	val mainAddressNo: String? = null,

	@field:SerializedName("address_name")
	val addressName: String? = null,

	@field:SerializedName("region_2depth_name")
	val region2depthName: String? = null,

	@field:SerializedName("region_3depth_h_name")
	val region3depthHName: String? = null,

	@field:SerializedName("region_1depth_name")
	val region1depthName: String? = null,

	@field:SerializedName("b_code")
	val bCode: String? = null,

	@field:SerializedName("zip_code")
	val zipCode: String? = null,

	@field:SerializedName("mountain_yn")
	val mountainYn: String? = null,

	@field:SerializedName("x")
	val X: String? = null,

	@field:SerializedName("sub_address_no")
	val subAddressNo: String? = null,

	@field:SerializedName("y")
	val Y: String? = null
)