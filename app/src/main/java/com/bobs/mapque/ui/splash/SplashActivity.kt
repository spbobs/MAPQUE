package com.bobs.mapque.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bobs.baselibrary.util.logd
import com.bobs.mapque.R
import com.bobs.mapque.ui.title.TitleActivity
import com.bobs.mapque.util.ext.checkLocationPermission
import com.gun0912.tedpermission.PermissionListener
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        // 권한 획득 체크
        checkLocationPermission(
            this,
            permissionListener
        )
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            logd(getString(R.string.permission_granted))
            // 권한 허용하였으므로 스플래쉬 애니메이션을 시작한다
            startSplash()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        logd("requestCode: $requestCode, resultCode: $resultCode, data: $data")
    }

    private fun startSplash() {
        tvSplash.clearAnimation()

        // 텍스트 페이드인 -> 페이드아웃
        ObjectAnimator.ofFloat(tvSplash, "alpha", 0f,1f).apply {
            duration = 1000
            addListener( object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    ObjectAnimator.ofFloat(tvSplash, "alpha", 1f,0f).apply {
                        duration = 2000
                        addListener( object : AnimatorListenerAdapter(){
                            override fun onAnimationEnd(animation: Animator?) {
                                startActivity(Intent(this@SplashActivity, TitleActivity::class.java))
                                finish()
                            }
                        })

                        start()
                    }
                }
            })

            start()
        }
    }
}