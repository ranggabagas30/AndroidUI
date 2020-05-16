package com.example.androidui.customview.circularindicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.View

class CircularIndicator(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    private var foregroundPaint: Paint
    private var backgroundPaint: Paint
    private var selectedAngle = 0
    private var clipPath: Path
    private var isFirstTime = true
    companion object{
        private const val DEFAULT_FG_COLOR = 0xffff0000.toInt()
        private const val DEFAULT_BG_COLOR = 0xffa0a0a0.toInt()
    }

    init {
        foregroundPaint = Paint()
        foregroundPaint.color = DEFAULT_FG_COLOR
        foregroundPaint.style = Paint.Style.FILL
        backgroundPaint = Paint()
        backgroundPaint.color = DEFAULT_BG_COLOR
        backgroundPaint.style = Paint.Style.FILL

        selectedAngle = 270
        clipPath = Path()
    }

    override fun onDraw(canvas: Canvas?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            // using 1:1 aspect ratio
            var circleSize = width
            if (height < circleSize) circleSize = height

            // create clip path arc using 0.75 of circle diameter
            if (isFirstTime) {
                val clipWidth = (circleSize * .75f).toInt()
                val clipX = (width - clipWidth) / 2
                val clipY = (height - clipWidth) / 2
                clipPath.addArc(clipX.toFloat(), clipY.toFloat(), (clipX + clipWidth).toFloat(), (clipY + clipWidth).toFloat(), 0f, 360f)
                isFirstTime = false
            }
            canvas?.clipRect(0f, 0f, width.toFloat(), height.toFloat())
            canvas?.clipPath(clipPath, Region.Op.DIFFERENCE)
            canvas?.save()
            canvas?.rotate(-90f, width/2.0f, height/2.0f)
            val horizontalMargin = (width - circleSize)/2
            val verticalMargin = (height - circleSize)/2
            val left = 0f + horizontalMargin
            val top = 0f + verticalMargin
            val right = left + circleSize
            val bottom = top + circleSize
            canvas?.drawArc(left, top, right, bottom, 0f, 360f, true, backgroundPaint)
            canvas?.drawArc(left, top, right, bottom, 0f, selectedAngle.toFloat(), true, foregroundPaint)
            canvas?.restore()
        }
    }
}