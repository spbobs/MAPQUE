package com.bobs.baselibrary.util

import com.orhanobut.logger.Logger

fun logv(msg: String) {
    Logger.v(msg)
}

fun logd(msg: String) {
    Logger.d(msg)
}

fun logi(msg: String) {
    Logger.i(msg)
}

fun logw(msg: String) {
    Logger.w(msg)
}

fun loge(msg: String) {
    Logger.e(msg)
}
