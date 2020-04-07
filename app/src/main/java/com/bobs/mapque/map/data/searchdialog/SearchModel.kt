package com.bobs.mapque.map.data.searchdialog

import ir.mirrajabi.searchdialog.core.Searchable

class SearchModel(private val title: String) : Searchable{
    // 서치 다이얼로그에서 보여줄 주소 타이틀을 저장한다
    override fun getTitle(): String = title
}