package com.bobs.mapque.network.response.searchaddress

import com.google.gson.annotations.SerializedName

data class Meta(
	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("is_end")
	val isEnd: Boolean? = null,

	@field:SerializedName("pageable_count")
	val pageableCount: Int? = null
)