package com.digiapt.digiapttemplate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.database.entities.User
import com.digiapt.digiapttemplate.listeners.AuthListener
import com.digiapt.digiapttemplate.trash.HomeActivity
import com.digiapt.digiapttemplate.trash.LoginActivity
import com.digiapt.digiapttemplate.viewmodels.AuthViewModel
import com.digiapt.digiapttemplate.viewmodelfactories.AuthViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashActivity : AppCompatActivity() , KodeinAware,
    AuthListener {

    override fun onStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private val SPLASH_TIME_OUT:Long = 750 // 3000 = 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)

            viewModel.authListener = this

            viewModel.getLoggedInUser().observe(this, Observer { user ->
                if(user == null){
                    Intent(this, LoginActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                } else {
                    Intent(this, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }
            })

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)

    }
}