package com.bobs.mapque.map.data.searchaddress

import com.bobs.mapque.network.api.SearchAddressService
import com.bobs.mapque.network.response.coord.CoordInfoResponse
import com.bobs.mapque.network.response.keyword.SearchKeyWordResponse
import com.bobs.mapque.network.response.searchaddress.SearchAddressResponse
import io.reactivex.Single

class SearchAddressRepository(private val api: SearchAddressService) :
    SearchAddressDataSource {
    // 주소 검색
    override fun getAddress(query: String): Single<SearchAddressResponse> = api.searchAddress(query = query)

    // 키워드 검색
    override fun getKeyword(
        query: String,
        latitude: Double,
        longitude: Double
    ): Single<SearchKeyWordResponse> = api.searchKeyword(query, latitude, longitude, radius = 1000)

    // 좌표 -> 주소 변환
    override fun getCoordInfo(latitude: Double, longitude: Double): Single<CoordInfoResponse> = api.getCoordInfo(longitude, latitude)
}