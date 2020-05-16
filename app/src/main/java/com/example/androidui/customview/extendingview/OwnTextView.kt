package com.example.androidui.customview.extendingview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.androidui.R
import com.example.androidui.util.UI

class OwnTextView constructor(context: Context, attributeSet: AttributeSet?): // primary constructor for instantiating view from XML
    AppCompatTextView(context, attributeSet) {

    constructor(context: Context): this(context, null) // instantiating on code

    private val DEFAULT_FILL_COLOR = 0xffff0000.toInt()
    private val DEFAULT_SIZE = UI.dpToPx(context, 300f)

    private var backgroundPaint: Paint = Paint()
    var fillColor2 = DEFAULT_FILL_COLOR
        set(value) {
            backgroundPaint.color = value
            field = value
        }

    var topLeftColor = DEFAULT_FILL_COLOR
        set(value) {
            needsUpdate = true
            field = value
        }
    var bottomLeftColor = DEFAULT_FILL_COLOR
        set(value) {
            needsUpdate = true
            field = value
        }
    var topRightColor = DEFAULT_FILL_COLOR
        set(value) {
            needsUpdate = true
            field = value
        }
    var bottomRightColor = DEFAULT_FILL_COLOR
        set(value) {
            needsUpdate = true
            field = value
        }
    private var needsUpdate = false

    init {
        val ta = context.theme.obtainStyledAttributes(attributeSet, R.styleable.OwnTextView, 0, 0)
        try {
            fillColor2 = ta.getColor(R.styleable.OwnTextView_fillColor, DEFAULT_FILL_COLOR)
        } finally {
            ta.recycle()
        }

        backgroundPaint.color = fillColor2
        backgroundPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("onMeasure ownTextView")
        println("widthMeasureSpec: ${MeasureSpec.toString(widthMeasureSpec)}")
        println("heightMeasureSpec: ${MeasureSpec.toString(heightMeasureSpec)}")
        val width = UI.getResolveSize(widthMeasureSpec, DEFAULT_SIZE.toInt())
        val height = UI.getResolveSize(heightMeasureSpec, DEFAULT_SIZE.toInt())
        println("measured width: $width")
        println("measured height: $height")
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        val left = 0f + paddingLeft
        val top = 0f + paddingTop
        val right = (width - paddingRight).toFloat()
        val bottom = (height - paddingBottom).toFloat()
        if (needsUpdate) {
            val colors = intArrayOf(topLeftColor, topRightColor, bottomLeftColor, bottomRightColor)
            val linearGradient = LinearGradient(left,  top, right, bottom, colors, null, Shader.TileMode.CLAMP)
            backgroundPaint.shader = linearGradient
            needsUpdate = false
        }
        canvas?.drawRect(left, top, right, bottom, backgroundPaint)
        super.onDraw(canvas)
    }
}