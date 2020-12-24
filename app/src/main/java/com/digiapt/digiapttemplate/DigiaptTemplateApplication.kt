package com.digiapt.digiapttemplate

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.digiapt.digiapttemplate.database.AppDatabase
import com.digiapt.digiapttemplate.network.Api
import com.explore.utils.network.NetworkConnectionInterceptor
import com.digiapt.digiapttemplate.preferences.PreferenceProvider
import com.digiapt.digiapttemplate.repositories.ContentsRepository
import com.digiapt.digiapttemplate.repositories.UserRepository
import com.digiapt.digiapttemplate.viewmodelfactories.AuthViewModelFactory
import com.digiapt.digiapttemplate.viewmodelfactories.ProfileViewModelFactory
import com.digiapt.digiapttemplate.viewmodelfactories.ContentsViewmodelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import com.digiapt.digiapttemplate.utils.AppSignatureHelper
import com.digiapt.digiapttemplate.utils.locale.LocaleManager

class DigiaptTemplateApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@DigiaptTemplateApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { Api(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }

        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { ContentsRepository(instance(), instance(), instance()) }

        bind() from provider {
            AuthViewModelFactory(
                instance()
            )
        }
        bind() from provider {
            ProfileViewModelFactory(
                instance()
            )
        }
        bind() from provider{
            ContentsViewmodelFactory(
                instance()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        val appSignatureHelper = AppSignatureHelper(this)
        appSignatureHelper.appSignatures
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }
}