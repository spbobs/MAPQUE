package com.bobs.mapque.network.response.keyword

import com.google.gson.annotations.SerializedName

data class DocumentsItem(

	@field:SerializedName("place_url")
	val placeUrl: String? = null,

	@field:SerializedName("place_name")
	val placeName: String? = null,

	@field:SerializedName("road_address_name")
	val roadAddressName: String? = null,

	@field:SerializedName("category_group_name")
	val categoryGroupName: String? = null,

	@field:SerializedName("category_name")
	val categoryName: String? = null,

	@field:SerializedName("distance")
	val distance: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("category_group_code")
	val categoryGroupCode: String? = null,

	@field:SerializedName("x")
	val X: String? = null,

	@field:SerializedName("y")
	val Y: String? = null,

	@field:SerializedName("address_name")
	val addressName: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)