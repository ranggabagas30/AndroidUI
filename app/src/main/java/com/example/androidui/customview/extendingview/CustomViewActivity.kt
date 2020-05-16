package com.example.androidui.customview.extendingview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.androidui.R

class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        val ownTextView = OwnTextView(this)
        ownTextView.fillColor2 = resources.getColor(R.color.colorAccent)
        ownTextView.topLeftColor = resources.getColor(R.color.colorPrimaryDark)
        ownTextView.topRightColor = resources.getColor(R.color.colorPrimaryVariant)
        ownTextView.bottomLeftColor = resources.getColor(R.color.colorPrimaryLightDark)
        ownTextView.bottomRightColor = resources.getColor(R.color.colorPrimaryLight)

        val ownTextView2 = OwnTextView2.Builder(this)
            .setTopLeftColor(resources.getColor(R.color.colorPrimaryDark))
            .setTopRightColor(resources.getColor(R.color.colorPrimaryVariant))
            .setBottomLeftColor(resources.getColor(R.color.colorPrimaryLightDark))
            .setBottomRightColor(resources.getColor(R.color.colorPrimaryLight))
            .build()
        linearLayout.addView(ownTextView)
        linearLayout.addView(ownTextView2)
        setContentView(linearLayout)
    }
}
