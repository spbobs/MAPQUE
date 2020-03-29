package com.bobs.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding> : Fragment() {
    lateinit var viewDataBinding: T

    abstract var layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

//    fun showLoading() {
//        val baseActivity = activity as BaseActivity<*>
//        baseActivity.showLoading()
//    }
//
//    fun hideLoading() {
//        val baseActivity = activity as BaseActivity<*>
//        baseActivity.hideLoading()
//    }
//
//    fun showKeyboard() {
//        val baseActivity = activity as BaseActivity<*>
//        baseActivity.showKeyboard()
//    }
//
//    fun hideKeyboard() {
//        val baseActivity = activity as BaseActivity<*>
//        baseActivity.hideKeyboard()
//    }
}