package com.bobs.mapque.searchlist.model.room

import android.os.AsyncTask

class SearchListRepository(private val searchItemDao: SearchItemDao) : SearchListData {
    // 룸의 경우 메인 스레드에서 접근을 기본적으로 허용하지 않으므로 AsyncTask로 접근한다.
    override fun getAllSearchList(): List<SearchItem> = searchItemDao.getAllSearchItems()

    override fun insert(searchItem: SearchItem) {
        AsyncTask.execute {
            searchItemDao.insertSearchItem(searchItem)
        }
    }

    override fun delete(searchItem: SearchItem) {
        AsyncTask.execute {
            searchItemDao.deleteSearchItem(searchItem)
        }
    }

    // 현재 버전에선 사용 안함
    override fun deleteAll() {
        AsyncTask.execute {
            searchItemDao.deleteAll()
        }
    }
}