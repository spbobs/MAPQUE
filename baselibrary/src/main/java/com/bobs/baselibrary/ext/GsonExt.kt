package com.bobs.baselibrary.ext

import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(json: String?, target: T? = null) : T? {
    if( json == null) return target

    return fromJson(json, T::class.java)
}

fun Any.toJson() = Gson().toJson(this)