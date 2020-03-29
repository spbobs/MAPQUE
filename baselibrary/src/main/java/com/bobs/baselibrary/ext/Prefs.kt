package com.bobs.baselibrary.ext

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Copyright (C) 2017 Tony Shen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

private inline fun<T> SharedPreferences.delegate(
    key: String? = null,
    defaultValue: T,
    crossinline getter: SharedPreferences.(String,T)->T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> = object: ReadWriteProperty<Any,T>{
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        getter(key ?: property.name, defaultValue)!!

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
        edit().setter(key ?: property.name, value).apply()
}

fun SharedPreferences.boolean(key: String? = null, defValue: Boolean = false) : ReadWriteProperty<Any, Boolean>{
    return delegate(key, defValue, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)
}