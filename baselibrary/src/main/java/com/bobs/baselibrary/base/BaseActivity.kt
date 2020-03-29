package com.bobs.baselibrary.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    lateinit var viewDataBinding: T

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.lifecycleOwner = this
    }

    fun showLoading() {

    }

    fun hideLoading() {

    }

    fun checkNetworking() {

    }

    fun showKeyboard() {
        val inputMethodManager =
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)

        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard() {
        val inputMethodManager =
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)

        inputMethodManager.hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
    }
}