package com.bobs.mapque.map.data.searchaddress

import com.bobs.mapque.network.api.SearchAddressService
import com.bobs.mapque.network.response.coord.CoordInfoResponse
import com.bobs.mapque.network.response.searchaddress.SearchAddressResponse
import io.reactivex.Single

class SearchAddressRepository(private val api: SearchAddressService) :
    SearchAddressDataSource {
    // 주소 검색 API를 호출
    override fun getAddress(query: String): Single<SearchAddressResponse> = api.searchAddress(query = query)

    // 좌표 -> 주소 변환 API를 호출
    override fun getCoordInfo(latitude: Double, longitude: Double): Single<CoordInfoResponse> = api.getCoordInfo(longitude, latitude)
}