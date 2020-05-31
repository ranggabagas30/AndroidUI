package com.example.androidui.util

import android.content.Context
import android.os.Handler
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import com.example.androidui.R


object Typography {
    fun downloadFontGooglePlayServicesProvider(context: Context, query: String, callback: FontsContractCompat.FontRequestCallback, handler: Handler) {
        val request = FontRequest(
            context.getString(R.string.google_fonts_provider_authority),
            context.getString(R.string.google_fonts_provider_package),
            query,
            R.array.com_google_android_gms_fonts_certs
        )
        FontsContractCompat.requestFont(context, request, callback, handler)
    }
}