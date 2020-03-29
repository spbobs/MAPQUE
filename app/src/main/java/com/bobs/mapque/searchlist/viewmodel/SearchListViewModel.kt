package com.bobs.mapque.searchlist.viewmodel

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bobs.baselibrary.base.BaseViewModel
import com.bobs.baselibrary.util.loge
import com.bobs.mapque.searchlist.model.room.SearchItem
import com.bobs.mapque.searchlist.model.room.SearchListData
import com.bobs.mapque.util.ext.sendCustomTemplate
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchListViewModel(private val searchListData: SearchListData) : BaseViewModel() {
    enum class ListCompareType(columnName: String) {
        SEARCH_QUERY("search_query"),
        SEARCH_DATE("search_date")
    }

    private var compareType =
        ListCompareType.SEARCH_DATE
    private var isAsc = false

    private var _ldSearchList: MutableLiveData<List<SearchItem>> = MutableLiveData()
    var ldSearchList: LiveData<List<SearchItem>> = _ldSearchList

    init {
        refreshList()
    }

    fun refreshList() {
        AsyncTask.execute {
            // postvalue는 메인스레드에서는 호출할 수 없다.
            _ldSearchList.postValue(searchListData.getAllSearchList())
        }
    }

    fun refreshList(list: List<SearchItem>) {
        // 인자로 받은 리스트로 livedata value 교체
        _ldSearchList.value = list
    }

    fun listCompare(type: ListCompareType, isAsc: Boolean) {
        // 리스트 정렬
        _ldSearchList.value?.let {
            val comparelist = it.toMutableList()

            when (type) {
                ListCompareType.SEARCH_QUERY -> {
                    if (isAsc) {
                        comparelist.sortBy { it.searchQuery }
                    } else {
                        comparelist.sortByDescending { it.searchQuery }
                    }
                }

                ListCompareType.SEARCH_DATE -> {
                    if (isAsc) {
                        comparelist.sortBy { it.searchDate }
                    } else {
                        comparelist.sortByDescending { it.searchDate }
                    }
                }
            }

            compareType = type
            this.isAsc = isAsc

            refreshList(comparelist)
        }
    }

    fun shareData(context: Context, searchItem: SearchItem) {
        // 카카오링크로 공유
        addDisposable(Single.just(searchItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sendCustomTemplate(
                    context,
                    it.searchAddressName!!,
                    it.searchLatitude,
                    it.searchLongitude
                )
            }, {
                loge("카카오링크 보내기 실패: ${it.message}")
            })
        )
    }

    fun delete(searchItem: SearchItem) {
        // 아이템 삭제
        searchListData.delete(searchItem)

        _ldSearchList.value?.let {
            val deletelist = it.toMutableList()
            deletelist.remove(searchItem)

            refreshList(deletelist)
        }
    }

    fun search(query: String): Boolean {
        // 검색 결과 처리
        if (query.isEmpty()) {
            refreshList()
            return true
        } else {
            _ldSearchList.value?.let {
                val searchList = it.toMutableList()
                    .filter { searchItem -> query in searchItem.searchQuery!! || query in searchItem.searchAddressName!! }

                if (searchList.isEmpty()) {
                    return false
                }

                refreshList(searchList)
            }
            return true
        }
    }

//    fun deleteAll() {
//        searchListData.deleteAll()
//        refreshList()
//    }
}