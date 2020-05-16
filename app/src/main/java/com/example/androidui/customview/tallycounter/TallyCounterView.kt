package com.example.androidui.customview.tallycounter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.androidui.R
import com.example.androidui.util.UI
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt

class TallyCounterView(context: Context, attrs: AttributeSet?) : View(context, attrs), TallyCounter {

    var displayedCount = "0"
    var currentCount = 0
    val MAX_COUNT = 9999
    val MAX_COUNT_STRING = MAX_COUNT.toString()

    var backgroundPaint: Paint
    var linePaint: Paint
    var numberPaint: Paint
    var backgroundRectF: RectF
    var cornerRadius: Float

    constructor(context: Context) : this(context, null)

    init {
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        linePaint.strokeWidth = 1f

        numberPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        numberPaint.color = ContextCompat.getColor(context, R.color.colorOnBackground)
        numberPaint.textSize = UI.fontSizeSp(context, 64f) // set text size = 64sp

        backgroundRectF = RectF() // allocate objects needed for canvas drawing here

        cornerRadius = (2f * context.resources.displayMetrics.density) // initialize drawing measurements

        setCount(0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // widthMeasureSpec and heightMeasureSpec consist of size and mode in one integer value
        // The combination is using bits of int
        val fontMetrics = numberPaint.fontMetrics

        // measure maximum possible width of text
        val maxTextWidth = numberPaint.measureText(MAX_COUNT_STRING)
        // estimate maximum possible height of text
        val maxTextHeight = -fontMetrics.top + fontMetrics.bottom

        // add padding to maximum width calculation
        val desiredWidth = (maxTextWidth + paddingLeft + paddingRight).roundToInt()

        // add padding to maximum height calculation
        val desiredHeight = (maxTextHeight + paddingTop + paddingBottom).roundToInt()

        // reconcile size that this view wants to be with the size the parent will let it be
        val measuredWidth = reconcileSize(desiredWidth, widthMeasureSpec)
        val measuredHeight = reconcileSize(desiredHeight, heightMeasureSpec)

        // store the final measured dimension
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        // drawing some light text on a pinkish background with
        // a line under it, and the text is centered in the canvas

        if (canvas != null) {
            // grab canvas dimensions
            val canvasWidth = width
            val canvasHeight = height

            // calculate horizontal center
            val centerxF = canvasWidth.times(0.5f)

            // draw the background
            backgroundRectF.set(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat())
            canvas.drawRoundRect(backgroundRectF, cornerRadius, cornerRadius, backgroundPaint)

            // draw baseline
            val baselineY = (canvasHeight * 0.6f).roundToInt().toFloat()
            canvas.drawLine(0f, baselineY, canvasWidth.toFloat(), baselineY, linePaint)

            // draw text
            // measure the width of text to display
            val textWidth = numberPaint.measureText(displayedCount)
            // figure out an x-coordinate that will center the text in the canvas
            val textX = (centerxF - textWidth * 0.5f).roundToInt().toFloat()
            // draw
            canvas.drawText(displayedCount, textX, baselineY, numberPaint)
        }
    }

    override fun reset() {
        setCount(0)
    }

    override fun increment() {
        setCount(currentCount + 1)
    }

    override fun getCount(): Int {
        return currentCount
    }

    override fun setCount(count: Int) {
        currentCount = min(count, MAX_COUNT)
        displayedCount = String.format(Locale.getDefault(), "%04d", currentCount)
        invalidate()
    }

    private fun reconcileSize(contentSize: Int, measureSpec: Int): Int{
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        Log.d("reconcile", "measureSpec: $measureSpec")
        Log.d("reconcile", "mode: $mode")
        Log.d("reconcile", "specSize: $specSize px")
        Log.d("reconcile", "specSize: ${UI.pxToDp(context, specSize.toFloat())} dp")
        Log.d("density", "density: ${context.resources.displayMetrics.density}")
        return when(mode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> {
                if (contentSize < specSize) {
                    contentSize
                } else {
                    specSize
                }
            }
            MeasureSpec.UNSPECIFIED -> contentSize
            else -> contentSize
        }
    }
}