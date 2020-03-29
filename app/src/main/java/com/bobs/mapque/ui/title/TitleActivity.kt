package com.bobs.mapque.ui.title

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.bobs.mapque.R
import com.bobs.mapque.ui.MainActivity

class TitleActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_title)

        // 아래쪽 텍스트 깜빡임 애니메이션을 준다
        val blinkAnimation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
        val blinkTextView = findViewById<TextView>(R.id.titleblink)
        blinkTextView.startAnimation(blinkAnimation)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // 아무 화면 터치시 메인액티비티를 시작한다
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()

            return true
        }
        return false
    }
}