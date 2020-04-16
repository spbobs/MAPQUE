package com.bobs.mapque.map.data.searchaddress

import com.bobs.mapque.network.response.coord.CoordInfoResponse
import com.bobs.mapque.network.response.keyword.SearchKeyWordResponse
import com.bobs.mapque.network.response.searchaddress.SearchAddressResponse
import io.reactivex.Single

interface SearchAddressDataSource {
    fun getAddress(query: String) : Single<SearchAddressResponse>

    fun getKeyword(query: String, latitude: Double, longitude: Double) : Single<SearchKeyWordResponse>

    fun getCoordInfo(latitude: Double, longitude: Double) : Single<CoordInfoResponse>
}