package com.example.androidui.customview.extendingview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import androidx.appcompat.widget.AppCompatTextView
import com.example.androidui.util.UI

@SuppressLint("ViewConstructor")
class OwnTextView2 private constructor(builder: Builder) : AppCompatTextView(builder.context) {

    private var backgroundPaint = Paint()
    private val DEFAULT_SIZE = UI.dpToPx(context, 300f)
    private val colorArray = intArrayOf(
        builder.topLeftColor,
        builder.topRightColor,
        builder.bottomLeftColor,
        builder.bottomRightColor
    )
    private var firstDraw = true
    init {
        backgroundPaint.style = Paint.Style.FILL
    }

    class Builder(var context: Context) {
        private val DEFAULT_COLOR = 0xffff0000.toInt()
        var topLeftColor = DEFAULT_COLOR
            private set
        var topRightColor = DEFAULT_COLOR
            private set
        var bottomLeftColor = DEFAULT_COLOR
            private set
        var bottomRightColor = DEFAULT_COLOR
            private set

        fun setTopLeftColor(color: Int) = apply {
            this.topLeftColor = color
        }

        fun setTopRightColor(color: Int) = apply {
            this.topRightColor = color
        }

        fun setBottomLeftColor(color: Int) = apply {
            this.bottomLeftColor = color
        }

        fun setBottomRightColor(color: Int) = apply {
            this.bottomRightColor = color
        }

        fun build() = OwnTextView2(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("width spec and mode: ${MeasureSpec.toString(widthMeasureSpec)}")
        println("height spec and mode: ${MeasureSpec.toString(heightMeasureSpec)}")
        val width = UI.getResolveSize(widthMeasureSpec, DEFAULT_SIZE.toInt())
        val height = UI.getResolveSize(heightMeasureSpec, DEFAULT_SIZE.toInt())
        println("measured width: $width")
        println("measured height: $height")
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        if (firstDraw) { // once defined first time, it can't be changed
            val linearGradient = LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), colorArray, null, Shader.TileMode.CLAMP)
            backgroundPaint.shader = linearGradient
            firstDraw = false
        }
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
        super.onDraw(canvas)
    }
}