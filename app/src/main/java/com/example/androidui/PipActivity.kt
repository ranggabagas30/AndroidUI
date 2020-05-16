package com.example.androidui

import android.app.AppOpsManager
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.util.DisplayMetrics
import android.util.Rational
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_pip.*
import kotlin.math.floor

class PipActivity : AppCompatActivity() {

    private lateinit var pipParams: PictureInPictureParams
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pip)

        btnEnterPipMode.setOnClickListener {
            // trigger to enter pip mode with a set of params defined
            invokePipMode()
        }
    }

    // to provide better experience, activity should listen to this in order to know
    // when activity enters PIP mode and configure it's layout accordingly
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        if (isInPictureInPictureMode) {
            println("on PIP mode")
            updateUiOnPipMode()
            // hide details
        } else {
            println("not on PIP mode")
            updateUiOnFullScreenMode()
            // show details
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
    }

    // when application is exited from PIP mode, the activity will enter on resume state
    override fun onResume() {
        super.onResume()
        println("onresume")
    }

    // You have to define when you want your application to enter on PiP mode — according to the Android Developers
    // documentation it’s suggested to be done on onUserLeaveHint()
    //
    // onUserLeaveHint() = Called as part of the activity lifecycle when an activity is about to go into the background
    // as the result of user choice. In cases when it is invoked, this method is called right before the
    // activity's onPause() callback.
    override fun onUserLeaveHint() {
        println("onuserleavehint")
        // to enter PIP mode the application must be run on Android OS Oreo above and with granted permissions
        invokePipMode()
    }

    override fun onPause() {
        super.onPause()
        println("onpause")
        // when activity in PIP mode, only in on pause, not on stop
        // we should define of how the activity UI looks like or the behaviour
        // of the activity itself when entering on pause state i.e., let it continue the works
        // or pause it. The UI size may be minimized, so that we can not see the details clearly.
        // Thus, we can hide those for instance.
    }

    override fun onStop() {
        super.onStop()
        println("onstop")
    }

    // PIP mode may be not allowed on low RAM device. Thus, we can check it using hasSystemFeature
    private fun isSupportPipMode() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)

    // Although not particularly easy to find, there’s a somewhat hidden permission for the user to disable picture-in-picture.
    // If you go to the native Settings → Your Application → Advanced → Picture-in-picture you have an option here
    // to disable it. For this scenario, I suggest before trying to enter in this mode to first verify if it’s allowed:
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun canEnterPipMode(): Boolean {
        val appOpsManager = this.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        return (AppOpsManager.MODE_ALLOWED == appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_PICTURE_IN_PICTURE,Process.myUid(), packageName))
    }

    private fun openPipSetting() {
        val intent = Intent(
            "android.settings.PICTURE_IN_PICTURE_SETTINGS",
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun getPipRatio(): Rational {
        // get optimal aspect ratio for PIP mode so it has consistency between fullscreen and this mode
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        var ratio = width.toDouble() / height.toDouble()
        if (ratio < .418410 || ratio > 2.39) {
            ratio = .5
            width = floor(height * ratio).toInt()
        }
        println("widht, height : $width, $height")
        return Rational(width, height)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun enterPipMode() {
        enterPictureInPictureMode(pipParams)
    }

    private fun invokePipMode() {
        when {
            isSupportPipMode() && !canEnterPipMode() -> openPipSetting()
            isSupportPipMode() && canEnterPipMode() -> {
                pipParams = PictureInPictureParams.Builder()
                    .setAspectRatio(getPipRatio())
                    .build()

                enterPipMode()
            }
            else -> Toast.makeText(this, "Does not support PIP mode. OS should be Oreo or above", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUiOnPipMode() {
        text.text = "PIP MODE"
        btnEnterPipMode.visibility = View.GONE
    }

    private fun updateUiOnFullScreenMode() {
        text.text = "FULLSCREEN MODE"
        btnEnterPipMode.visibility = View.VISIBLE
    }
}
