package com.example.androidui.util

import android.content.Context
import android.icu.util.Measure
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import kotlin.math.min
import kotlin.math.roundToInt

object UI {
    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, context.resources.displayMetrics)
    }

    fun pxToDp(context: Context, px: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.resources.displayMetrics)
    }

    fun fontSizeSp(context: Context, fontSize: Float): Float {
        return (fontSize * context.resources.displayMetrics.scaledDensity)
    }

    fun getModeString(mode: Int): String {
        return when(mode) {
            View.MeasureSpec.AT_MOST -> "AT_MOST" // view can be as big as it wants no more than the available size
            View.MeasureSpec.EXACTLY -> "EXACTLY" // the size is determined by parents. View will require that size or return different size
            View.MeasureSpec.UNSPECIFIED -> "UNSPECIFIED"
            else -> "NOT KNOWN"
        }
    }

    fun getResolveSize(measureSpec: Int, defaultSize: Int): Int {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        return when(mode) {
            View.MeasureSpec.EXACTLY -> size
            View.MeasureSpec.AT_MOST -> min(defaultSize, size)
            View.MeasureSpec.UNSPECIFIED -> defaultSize
            else -> defaultSize
        }
    }

    fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            var p = (view.layoutParams as ViewGroup.MarginLayoutParams)
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
}