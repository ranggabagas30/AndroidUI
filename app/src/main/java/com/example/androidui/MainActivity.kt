package com.example.androidui

import android.content.Context
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

        requestDownloadFontFamily("Margarine")
        setFontProgrammatically()
    }

    /**
     * download font
     *
     * */
    private fun requestDownloadFontFamily(fontFamily: String) {
        val query = QueryBuilder(fontFamily).build()
        Typography.downloadFontGooglePlayServicesProvider(this, query, object : FontsContractCompat.FontRequestCallback() {
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
        val typeface = ResourcesCompat.getFont(this, R.font.cactus_love)
        val typeface2 = ResourcesCompat.getFont(this, R.font.aclonica)
        val typeface3 = ResourcesCompat.getFont(this, R.font.custom_font_family)
        tv_typeface.typeface = typeface
        tv_typeface2.typeface = typeface2
        tv_typeface3.typeface = typeface3
    }
}
