package com.digiapt.digiapttemplate.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.pm.PackageManager.GET_META_DATA
import com.digiapt.digiapttemplate.utils.locale.LocaleManager

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetTitles()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    protected fun resetTitles() {
        try {
            val info = packageManager.getActivityInfo(componentName, GET_META_DATA)
            if (info.labelRes != 0) {
                setTitle(info.labelRes)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }
}