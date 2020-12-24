package com.digiapt.digiapttemplate.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import java.util.*

class LanguageUtils {
    companion object{

        fun changeLanguage(context: Context, languageCode : String){
            val res = context.getResources()
            // Change locale settings in the app.
            val dm = res.getDisplayMetrics()
            val conf = res.getConfiguration()
            conf.setLocale(Locale(languageCode.toLowerCase())) // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm)
        }
    }
}