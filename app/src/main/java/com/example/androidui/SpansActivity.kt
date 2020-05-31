package com.example.androidui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import com.example.androidui.util.UI
import kotlinx.android.synthetic.main.activity_pip.*
import kotlinx.android.synthetic.main.activity_spans.*

class SpansActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spans)

        /**
         * Spannable flags
         * */
        // demonstration of Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        var spannableText1 = SpannableStringBuilder("Text is spantastic!")
        spannableText1.setSpan(
            ForegroundColorSpan(Color.RED),
            8, // start
        12, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        text1.text = spannableText1

        spannableText1.insert(12, "(& fon)")
        text2.text = spannableText1

        // demonstration of Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        spannableText1 = SpannableStringBuilder("Text is spantastic!")
        spannableText1.setSpan(
            ForegroundColorSpan(Color.RED),
            8,
            12,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text3.text = spannableText1

        spannableText1.insert(12, "(& fon)")
        text4.text = spannableText1

        // demonstration of Spannable.SPAN_INCLUSIVE_INCLUSIVE
        spannableText1 = SpannableStringBuilder("Text is spantastic!")
        spannableText1.setSpan(
            ForegroundColorSpan(Color.RED),
            8,
            12,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        text5.text = spannableText1

        spannableText1.insert(12, "(& fon)")
        text6.text = spannableText1

        // demonstration of Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        spannableText1 = SpannableStringBuilder("Text is spantastic!")
        spannableText1.setSpan(
            ForegroundColorSpan(Color.RED),
            8,
            12,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        text7.text = spannableText1

        spannableText1.insert(12, "(& fon)")
        text8.text = spannableText1

        /**
         * Paragraph span
         * */
        // demonstration of BulletSpan
        val gapWidthPx = UI.dpToPx(this, 16f)
        val accentColor = resources.getColor(R.color.colorAccent)
        val spannableText2 = SpannableStringBuilder("My text \nbullet one \nbullet two")
        spannableText2.setSpan(
            BulletSpan(gapWidthPx.toInt(), accentColor),
            9, // start
        18, // end
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableText2.setSpan(
            BulletSpan(gapWidthPx.toInt(), accentColor),
            20,
            spannableText2.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text9.text = spannableText2

        // demonstration of Html text styling
        val htmlText = "My text <ul><li>bullet one</li><li>bullet two</li></ul>"
        text10.text = Html.fromHtml(htmlText)

        /**
         * Optimize spanning text
         *
         * instead of using TextView.setText(CharSequence), use TextView.setText(Spannable, BufferType.SPANNABLE)
         * */
        val spannableText3 = SpannableString("Spantastic text")
        text11.setText(spannableText3, TextView.BufferType.SPANNABLE)

        // we don't have to do TextView.setText(CharSequence) again because we're modifying directly instance of
        // the CharSequence object held by TextView
        // getting the instance of the text object held by the TextView
        // this can be casted to Spannable as before we already set it as BufferType.SPANNABLE
        val spannableText3New = text11.text as Spannable

        // apply appearance affecting span which will call TextView.onDraw only, not TextView.onLayout
        // Text is redrawn but not the width and height
        spannableText3New.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorError)),
            0,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableText3New.setSpan(
            RelativeSizeSpan(2f),
            0, 4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    }
}