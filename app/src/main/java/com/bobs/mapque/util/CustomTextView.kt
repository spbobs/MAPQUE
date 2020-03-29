package com.bobs.mapque.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Join
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.bobs.mapque.R

// 외곽선 표시용 텍스트뷰
class CustomTextView : AppCompatTextView {
    private var strokeWidth = 0f
    private var strokeColor: Int? = null
    private var strokeJoin: Join? = null
    private var strokeMiter = 0f

    constructor(context: Context?) : super(context!!) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context!!, attrs, defStyle) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            @SuppressLint("Recycle") val a: TypedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView)
            if (a.hasValue(R.styleable.CustomTextView_strokeColor)) {
                val strokeWidth =
                    a.getDimensionPixelSize(R.styleable.CustomTextView_strokeWidth, 1).toFloat()
                val strokeColor =
                    a.getColor(R.styleable.CustomTextView_strokeColor, -0x1000000)
                val strokeMiter =
                    a.getDimensionPixelSize(R.styleable.CustomTextView_strokeMiter, 10).toFloat()
                var strokeJoin: Join? = null
                when (a.getInt(R.styleable.CustomTextView_strokeJoinStyle, 0)) {
                    0 -> strokeJoin = Join.MITER
                    1 -> strokeJoin = Join.BEVEL
                    2 -> strokeJoin = Join.ROUND
                }
                setStroke(strokeWidth, strokeColor, strokeJoin, strokeMiter)
            }
        }
    }

    fun setStroke(width: Float, color: Int, join: Join?, miter: Float) {
        strokeWidth = width
        strokeColor = color
        strokeJoin = join
        strokeMiter = miter
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val restoreColor: Int = this.getCurrentTextColor()

        if (strokeColor != null) {
            val paint: TextPaint = this.getPaint()
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = strokeJoin
            paint.strokeMiter = strokeMiter
            this.setTextColor(strokeColor!!)
            paint.strokeWidth = strokeWidth
            super.onDraw(canvas)
            paint.style = Paint.Style.FILL
            this.setTextColor(restoreColor)
        }
    }
}