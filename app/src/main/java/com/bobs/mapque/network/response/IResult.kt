package com.bobs.mapque.network.response

interface IResult<T> {
    fun success(result: T)
    fun fail(msg: String? = null)
}