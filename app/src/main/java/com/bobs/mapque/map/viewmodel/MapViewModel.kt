package com.bobs.mapque.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bobs.baselibrary.base.BaseViewModel
import com.bobs.baselibrary.util.logd
import com.bobs.baselibrary.util.loge
import com.bobs.mapque.searchlist.data.model.SearchItem
import com.bobs.mapque.searchlist.data.source.SearchListDataSource
import com.bobs.mapque.map.data.searchaddress.SearchAddressDataSource
import com.bobs.mapque.network.response.IResult
import com.bobs.mapque.network.response.coord.DocumentsItem
import com.bobs.mapque.network.response.searchaddress.SearchAddressResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

class MapViewModel(
    private val model: SearchAddressDataSource,
    private val searchListDataSource: SearchListDataSource
) : BaseViewModel() {
    private val _searchAddressData: MutableLiveData<SearchAddressResponse> = MutableLiveData()
    val searchAddressData: LiveData<SearchAddressResponse> = _searchAddressData

    fun getSearchAddress(query: String, iResult: IResult<String>) {
        // 주소 검색 결과 처리
        addDisposable(
            model.getAddress(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if (meta?.totalCount!! > 0) {
                            logd("meta: $meta")
                            // livedata의 setvalue는 메인쓰레드에서 한다.
                            _searchAddressData.value = this
                            iResult.success("")
                        } else {
                            iResult.fail()
                        }
                    }
                }, {
                    loge("response error: ${it.message}")
                    iResult.fail(it.message)
                })
        )
    }

    fun getCoordInfo(latitude: Double, longitude: Double, iResult: IResult<DocumentsItem?>){
        // 좌표 -> 주소 변환 결과 처리
        addDisposable(
            model.getCoordInfo(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run{
                        if(meta?.totalCount!! > 0){
                            iResult.success(it.documents!![0])
                        } else {
                            iResult.fail()
                        }
                    }
                },{
                    loge("response error: ${it.message}")
                    iResult.fail(it.message)
                })
        )
    }

    fun insertSearchAddress(
        searchQuery: String?,
        address: String?,
        latitude: Double,
        longitude: Double,
        date: DateTime
    ) {
        // room에 저장
        val searchItem =
            SearchItem(
                0,
                searchQuery,
                address,
                latitude,
                longitude,
                date
            )
        searchListDataSource.insert(searchItem)
    }
}