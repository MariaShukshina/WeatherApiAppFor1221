package com.example.weatherapiapp.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Constants @Inject constructor(@ApplicationContext private val context: Context) {

    val key = provideKey()

    private fun provideKey(): String {
        val ai: ApplicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val key = ai.metaData["keyValue"].toString()
        return key
    }

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }

}