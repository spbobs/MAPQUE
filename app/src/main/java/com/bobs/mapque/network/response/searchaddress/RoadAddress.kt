package com.bobs.mapque.network.response.searchaddress

import com.google.gson.annotations.SerializedName

data class RoadAddress(
	@field:SerializedName("road_name")
	val roadName: String? = null,

	@field:SerializedName("main_building_no")
	val mainBuildingNo: String? = null,

	@field:SerializedName("building_name")
	val buildingName: String? = null,

	@field:SerializedName("region_3depth_name")
	val region3depthName: String? = null,

	@field:SerializedName("underground_yn")
	val undergroundYn: String? = null,

	@field:SerializedName("sub_building_no")
	val subBuildingNo: String? = null,

	@field:SerializedName("x")
	val X: String? = null,

	@field:SerializedName("y")
	val Y: String? = null,

	@field:SerializedName("address_name")
	val addressName: String? = null,

	@field:SerializedName("region_2depth_name")
	val region2depthName: String? = null,

	@field:SerializedName("zone_no")
	val zoneNo: String? = null,

	@field:SerializedName("region_1depth_name")
	val region1depthName: String? = null
)