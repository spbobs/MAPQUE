package com.bobs.mapque.keyhash

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bobs.baselibrary.base.BaseViewModel
import com.bobs.baselibrary.util.getKeyHash
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class KeyHashViewModel : BaseViewModel() {
    private val _keyHashData: MutableLiveData<KeyHashData> = MutableLiveData<KeyHashData>()
    val keyHashData: LiveData<KeyHashData>
        get() = _keyHashData

    fun setKeyHash(activity: Activity) {
        addDisposable(
            Observable.just(_keyHashData)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        it.value = KeyHashData(getKeyHash(activity))
                }, {
                })
        )
    }
}