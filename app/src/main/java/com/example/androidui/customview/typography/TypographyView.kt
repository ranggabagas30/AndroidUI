package com.example.androidui.customview.typography

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.androidui.R
import com.example.androidui.util.UI
import kotlin.math.min

class TypographyView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
): View(context, attributeSet) {

    private var backgroundPaint: Paint
    private var textPaint: TextPaint
    private var topLinePaint: Paint
    private var bottomLinePaint: Paint
    private var ascentPaint: Paint
    private var descentPaint: Paint
    private var baselinePaint: Paint
    private var backgroundMidPaint: Paint

    // set background spec
    private var backgroundWidth = 640 // px
    private var backgroundHeight = 640 // px
    private var backgroundRoundRectF: RectF

    // set text spec
    private var text = "This is text"
    private var textFontSize = 32f // sp
    private val textPaddingTop = 10 // dp
    private val textPaddingBottom = 10 // dp
    private val textPaddingStart = 10 // dp
    private val textPaddingEnd = 10 // dp
    private var textStartX = 0f
    private var textStartY = 0f
    private val textBoundRect by lazy {
        Rect()
    }

    // set line spec
    private val lineStrokeWidth = 1f // dp

    init {
        backgroundPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorBackgroundTypography)
            flags = Paint.ANTI_ALIAS_FLAG
        }
        textPaint = TextPaint().apply {
            color = ContextCompat.getColor(context, R.color.colorTextTypography)
            flags = Paint.ANTI_ALIAS_FLAG
            textSize = UI.fontSizeSp(context, textFontSize)
        }
        topLinePaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorTopLineTypography)
            strokeWidth = UI.dpToPx(context, lineStrokeWidth)
        }
        bottomLinePaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorBottomLineTypography)
            strokeWidth = UI.dpToPx(context, lineStrokeWidth)
        }
        ascentPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorAscentTypography)
            strokeWidth = UI.dpToPx(context, lineStrokeWidth)
        }
        descentPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorDescentTypography)
            strokeWidth = UI.dpToPx(context, lineStrokeWidth)
        }
        baselinePaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorBaselineTypography)
            strokeWidth = UI.dpToPx(context, lineStrokeWidth)
        }
        backgroundMidPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.colorOnPrimary)
            strokeWidth = UI.dpToPx(context, lineStrokeWidth)
        }

        backgroundRoundRectF = RectF()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("width spec: ${MeasureSpec.toString(widthMeasureSpec)}")
        println("height spec: ${MeasureSpec.toString(heightMeasureSpec)}")

        // set square background dimension
        backgroundWidth = reconcileSize(backgroundWidth, widthMeasureSpec)
        backgroundHeight = backgroundWidth

        println("background width: $backgroundWidth")
        println("background height: $backgroundHeight")

        setMeasuredDimension(backgroundWidth, backgroundHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {

            // define text draw position
            val backgroundCenterX = backgroundWidth.toFloat() / 2f
            val backgroundCenterY = backgroundHeight.toFloat() / 2f

            val textWidthMax = backgroundWidth - (textPaddingStart + textPaddingEnd)

            //textPaint.getTextBounds(text, 0, text.length, textBoundRect)
            val textFontMetrics = textPaint.fontMetrics
            println("top: ${textFontMetrics.top}")
            println("ascent: ${textFontMetrics.ascent}")
            println("descent: ${textFontMetrics.descent}")
            println("bottom: ${textFontMetrics.bottom}")
            println("leading: ${textFontMetrics.leading}")

            textPaint.getTextBounds(text, 0, text.length, textBoundRect)
            val textWidth = textBoundRect.width()
            val textHeight = textBoundRect.height()
            val textHalfWidth = textBoundRect.exactCenterX()
            val textHalfHeight = textBoundRect.exactCenterY()

            println("text width: $textWidth")
            println("text height: $textHeight")
            println("text half width: $textHalfWidth")
            println("text half height $textHalfHeight")

            if (textWidth > textWidthMax) {
                Toast.makeText(context, "Width of the text is out of bound", Toast.LENGTH_SHORT).show()
                return
            }

            textStartX = backgroundCenterX - textHalfWidth
            textStartY = backgroundCenterY - textHalfHeight

            // draw background
            val cornerRadius = UI.dpToPx(context, 10f)
            backgroundRoundRectF.set(0f, 0f, backgroundWidth.toFloat(), backgroundHeight.toFloat())
            canvas.drawRoundRect(backgroundRoundRectF, cornerRadius, cornerRadius, backgroundPaint)

            // draw text8
            canvas.drawText(text, textStartX, textStartY, textPaint)

            // draw top line
            val baseLine = backgroundCenterY
            val topLine = baseLine + textFontMetrics.top
            println("absolute topline position: $topLine")
            canvas.drawLine(0f, topLine, backgroundWidth.toFloat(), topLine, topLinePaint)

            // draw background middle line
            //canvas.drawLine(0f, backgroundCenterY, backgroundWidth.toFloat(), backgroundCenterY, backgroundMidPaint)
        }
    }

    private fun reconcileSize(desiredSize: Int, measureSpec: Int): Int {
        val specSize = MeasureSpec.getSize(measureSpec)
        val specMode = MeasureSpec.getMode(measureSpec)

        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            MeasureSpec.AT_MOST -> {
                min(desiredSize, specSize)
            }
            else -> {
                println("Parent defines unspecified")
                desiredSize
            }
        }
    }
}