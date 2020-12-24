package com.digiapt.digiapttemplate.trash

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.activities.BaseActivity
import com.digiapt.digiapttemplate.utils.LanguageUtils.Companion.changeLanguage
import com.digiapt.digiapttemplate.utils.locale.LocaleManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(this,navController, drawer_layout)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            drawer_layout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.locale_english -> {
                setNewLocale(this, LocaleManager.ENGLISH)
                return true
            }
            R.id.locale_hindi -> {
                setNewLocale(this, LocaleManager.HINDI)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNewLocale(mContext: AppCompatActivity, language: String) {
        changeLanguage(mContext,language)
//        LocaleManager.setNewLocale(this, language)
        val intent = mContext.intent
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }

}