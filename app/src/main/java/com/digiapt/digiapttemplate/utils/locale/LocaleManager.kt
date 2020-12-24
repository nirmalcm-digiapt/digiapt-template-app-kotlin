package com.digiapt.digiapttemplate.utils.locale

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.Locale

object LocaleManager {
    internal val ENGLISH = "en"
    internal val HINDI = "hi"
    /**
     * SharedPreferences Key
     */
    private const val LANGUAGE_KEY = "language_key"

    /**
     * set current pref locale
     */
    fun setLocale(mContext: Context): Context {
        return updateResources(mContext, getLanguagePref(mContext))
    }

    /**
     * Set new Locale with context
     */
    fun setNewLocale(mContext: Context, language: String): Context {
        setLanguagePref(mContext, language)
        return updateResources(mContext, language)
    }

    /**
     * Get saved Locale from SharedPreferences
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
    fun getLanguagePref(mContext: Context): String {
        val mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        val pref = mPreferences.getString(LANGUAGE_KEY, ENGLISH)
        return pref!!
    }

    /**
     * set pref key
     */
    private fun setLanguagePref(mContext: Context, localeKey: String) {
        val mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        mPreferences.edit().putString(LANGUAGE_KEY, localeKey).apply()
    }

    /**
     * update resource
     */
    private fun updateResources(context: Context, language: String): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    /**
     * get current locale
     */
    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
    }
}