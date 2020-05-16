package com.example.androidui

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.provider.FontsContractCompat
import com.example.androidui.util.QueryBuilder
import com.example.androidui.util.Typography
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { v ->
            val intent = Intent(this, ActivityNoActionBar::class.java)
            startActivity(intent)
        }
        elevation_control.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button1.elevation = progress.toFloat()
                    button1.text = "Rais/Lower Me ${progress.toFloat()}"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        requestDownloadFont("Margarine")

        setFontProgrammatically()
    }

    private fun requestDownloadFont(fontFamily: String) {
        val query = QueryBuilder(fontFamily).build()
        Typography.downloadFont(this, query, object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                Log.d("success", "success download font ${typeface.toString()}")
                tv_font_runtime.typeface = typeface
            }
            override fun onTypefaceRequestFailed(reason: Int) {
                Toast.makeText(this@MainActivity, "failed reason: $reason", Toast.LENGTH_LONG).show()
            }
        }, getHandlerThreadHandler())
    }

    private fun getHandlerThreadHandler(): Handler {
        if (mHandler == null) {
            val handlerThread = HandlerThread("fonts")
            handlerThread.start()
            mHandler = Handler(handlerThread.looper)
        }
        return mHandler as Handler
    }

    private fun setFontProgrammatically() {
        val typeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(R.font.custom_font_family)
        } else {
            //Typeface.createFromAsset(assets, "fonts/cactus_love.ttf")
            ResourcesCompat.getFont(this, R.font.aclonica)
        }
        tv_typeface.typeface = typeface
    }
}
