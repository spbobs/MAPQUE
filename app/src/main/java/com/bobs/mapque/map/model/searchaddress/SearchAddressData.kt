package com.bobs.mapque.map.model.searchaddress

import com.bobs.mapque.network.response.coord.CoordInfoResponse
import com.bobs.mapque.network.response.searchaddress.SearchAddressResponse
import io.reactivex.Single

interface SearchAddressData {
    fun getAddress(query: String) : Single<SearchAddressResponse>

    fun getCoordInfo(latitude: Double, longitude: Double) : Single<CoordInfoResponse>
}