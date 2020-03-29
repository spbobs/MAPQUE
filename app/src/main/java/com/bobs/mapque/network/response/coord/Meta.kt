package com.bobs.mapque.network.response.coord

import com.google.gson.annotations.SerializedName

data class Meta(
	@field:SerializedName("total_count")
	val totalCount: Int? = null
)