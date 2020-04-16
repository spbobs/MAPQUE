package com.bobs.mapque.network.response.keyword

import com.google.gson.annotations.SerializedName

data class SameName(

	@field:SerializedName("region")
	val region: List<Any?>? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null,

	@field:SerializedName("selected_region")
	val selectedRegion: String? = null
)