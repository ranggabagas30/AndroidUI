package com.example.androidui.customview.extendingviewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.*
import com.example.androidui.util.UI

class CustomLayout(context: Context, attributeSet: AttributeSet?): ViewGroup(context, attributeSet) {

    constructor(context: Context): this(context, null)

    // calculating maximum width and height that will be needed
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("onMeasure")
        println("width measure spec: ${MeasureSpec.toString(widthMeasureSpec)}")
        println("height measure spec: ${MeasureSpec.toString(heightMeasureSpec)}")
        val count = childCount
        var rowHeight = 0
        var maxWidth = 0
        var maxHeight: Int
        var left = 0
        var top = 0

        for (i in 0 until count) {
            val child = getChildAt(i)
            val childParams = child.layoutParams as MarginLayoutParams
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val childWidth = child.measuredWidth + child.paddingLeft + child.paddingRight + child.marginLeft + child.marginRight
            val childHeight = child.measuredHeight + child.paddingTop + child.paddingBottom + child.marginTop + child.marginBottom
            println("child $i : ")
            println("-> measured width = ${child.measuredWidth}")
            println("-> measured height = ${child.measuredHeight}")
            println("-> width = ${childWidth}")
            println("-> height = ${childHeight}")
            println("-> margin = ${child.marginLeft}, ${child.marginTop}, ${child.marginRight}, ${child.marginBottom}")
            println("-> margin (from layoutParams) = ${childParams.leftMargin}, ${childParams.topMargin}, ${childParams.rightMargin}, ${childParams.bottomMargin}")

            // if child fits in this row put it there
            if (left + childWidth < width) {
                left += childWidth
            } else {
                // otherwise put it on next row
                if (left > maxWidth) maxWidth = left
                left = 0
                top += rowHeight
                rowHeight = 0
            }

            // update maximum row height
            if (childHeight > rowHeight) rowHeight = childHeight
        }

        if (left > maxWidth) maxWidth = left
        maxHeight = top + rowHeight

        setMeasuredDimension(UI.getResolveSize(widthMeasureSpec, maxWidth), UI.getResolveSize(heightMeasureSpec, maxHeight))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        println("onLayout")
        println("changed? $changed")
        println("l = $l, t = $t, r = $r, b = $b")
        var count = childCount
        var left = l + paddingLeft
        var top = t + paddingTop

        // keep tracks of maximum row height
        var rowHeight = 0

        for (i in 0 until count) {
            println("child $i : ")
            val child = getChildAt(i)
            val childWidth = child.measuredWidth + child.paddingLeft + child.paddingRight + child.marginLeft + child.marginRight
            val childHeight = child.measuredHeight + child.paddingTop + child.paddingBottom + child.marginTop + child.marginBottom

            if (left + childWidth < r - paddingRight) {
                val posStart = left + child.marginStart
                val posEnd   = left + childWidth - child.marginEnd
                val posTop   = top + child.marginTop
                val posBottom = top + childHeight - child.marginBottom
                child.layout(posStart, posTop, posEnd, posBottom)
                left += childWidth
                println("layout position = $posStart, $posTop, $posEnd, $posBottom")
            } else {
                // put it on the next row
                left = l + paddingLeft
                top += rowHeight
                rowHeight = 0
                println("put on the next row")
            }

            // update maximum row height
            if (childHeight > rowHeight) rowHeight = childHeight
        }
    }
}