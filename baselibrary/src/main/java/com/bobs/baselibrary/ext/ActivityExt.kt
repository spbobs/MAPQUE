package com.bobs.baselibrary.ext

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(targetFragment: Fragment, fragResID: Int) {
    supportFragmentManager.beginTransaction().replace(fragResID, targetFragment).commit()
}

fun AppCompatActivity.addFragment(targetFragment: Fragment, fragResID: Int, tag: String) {
    supportFragmentManager.beginTransaction().add(fragResID, targetFragment, tag).commit()
}

fun AppCompatActivity.removeFragment(targetFragment: Fragment) {
    supportFragmentManager.beginTransaction().remove(targetFragment).commit()
}

fun AppCompatActivity.showFragment(targetFragment: Fragment) {
    supportFragmentManager.beginTransaction().show(targetFragment).commit()
}

fun AppCompatActivity.hideFragment(targetFragment: Fragment) {
    supportFragmentManager.beginTransaction().hide(targetFragment).commit()
}

fun AppCompatActivity.addToBackStackFragment(name: String?) {
    supportFragmentManager.beginTransaction().addToBackStack(name).commit()
}

fun AppCompatActivity.toast(msg: String, longToast: Boolean = false) {
    Toast.makeText(
        applicationContext,
        msg,
        if (longToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}

fun AppCompatActivity.toast(msgResID: Int, longToast: Boolean = false) {
    Toast.makeText(
        applicationContext,
        msgResID,
        if (longToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}