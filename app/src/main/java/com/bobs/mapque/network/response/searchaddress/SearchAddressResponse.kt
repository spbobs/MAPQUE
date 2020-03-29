package com.bobs.mapque.network.response.searchaddress

import com.google.gson.annotations.SerializedName

data class SearchAddressResponse(
	@field:SerializedName("documents")
	val documents: List<DocumentsItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)