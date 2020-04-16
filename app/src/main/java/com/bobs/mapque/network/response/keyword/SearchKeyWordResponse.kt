package com.bobs.mapque.network.response.keyword

import com.google.gson.annotations.SerializedName

data class SearchKeyWordResponse(

    @field:SerializedName("documents")
	val documents: List<DocumentsItem?>? = null,

    @field:SerializedName("meta")
	val meta: Meta? = null
)