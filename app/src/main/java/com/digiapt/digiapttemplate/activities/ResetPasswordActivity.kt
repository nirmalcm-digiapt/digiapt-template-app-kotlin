package com.digiapt.digiapttemplate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main._activity_reset_password.*
import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.utils.toast

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout._activity_reset_password)

        button_reset_password.setOnClickListener {
            val email = text_email.text.toString().trim()

            if (email.isEmpty()) {
                text_email.error = "Email Required"
                text_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                text_email.error = "Valid Email Required"
                text_email.requestFocus()
                return@setOnClickListener
            }

            progressbar.visibility = View.VISIBLE

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    progressbar.visibility = View.GONE
                    if (task.isSuccessful) {
                        this.toast("Check your email")
                    } else {
                        this.toast(task.exception?.message!!)
                    }
                }
        }
    }
}
