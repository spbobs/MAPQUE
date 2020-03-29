package com.bobs.mapque.network.response.coord

import com.google.gson.annotations.SerializedName

data class CoordInfoResponse(
	@field:SerializedName("documents")
	val documents: List<DocumentsItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)