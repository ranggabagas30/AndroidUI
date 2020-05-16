package com.example.androidui.customview.extendingviewgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.marginBottom
import com.example.androidui.R
import com.example.androidui.customview.extendingview.OwnTextView
import com.example.androidui.util.UI
import kotlinx.android.synthetic.main.activity_custom_view_group.*
import java.util.*

class CustomViewGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view_group)

        val rnd = Random()
        for (i in 0 .. 49) {
            val view = OwnTextView(this)
            val width = rnd.nextInt(200) + 50 // MeasureSpec mode = EXACTLY
            val height = rnd.nextInt(100) + 100 // MeasureSpec mode = EXACTLY
            view.setPadding(
                2,
                2,
                2,
                2
            )
            println("child $i :")
            println("-> width = $width, width + padding = ${width + view.paddingLeft + view.paddingRight}")
            println("-> height = $height, height + padding = ${height + view.paddingTop + view.paddingBottom}")
            val viewParams = ViewGroup.MarginLayoutParams(width, height)
            viewParams.setMargins(10, 10, 10, 10)
            view.layoutParams = viewParams
            custom_layout.addView(view)
        }
    }
}
