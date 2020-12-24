package com.digiapt.digiapttemplate.trash

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.listeners.OtpReceivedInterface
import com.digiapt.digiapttemplate.receivers.SmsBroadcastReceiver
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    OtpReceivedInterface,
    GoogleApiClient.OnConnectionFailedListener {
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mSmsBroadcastReceiver: SmsBroadcastReceiver
    private val RESOLVE_HINT = 2
    lateinit var inputMobileNumber: EditText
    lateinit var inputOtp: EditText
    lateinit var btnGetOtp: Button
    lateinit var btnVerifyOtp: Button
    lateinit var layoutInput: ConstraintLayout
    lateinit var layoutVerify: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        // init broadcast receiver
        mSmsBroadcastReceiver = SmsBroadcastReceiver()
        //set google api client for hint request
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, this)
            .addApi(Auth.CREDENTIALS_API)
            .build()
        mSmsBroadcastReceiver.setOnOtpListeners(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        applicationContext.registerReceiver(mSmsBroadcastReceiver, intentFilter)
        // get mobile number from phone
        getHintPhoneNumber()
        btnGetOtp.setOnClickListener {
            // Call server API for requesting OTP and when you got success start
            // SMS Listener for listing auto read message lsitner
            startSMSListener()
        }
    }

    private fun initViews() {
        inputMobileNumber = findViewById(R.id.editTextInputMobile)
        inputOtp = findViewById(R.id.editTextOTP)
        btnGetOtp = findViewById(R.id.buttonGetOTP)
        btnVerifyOtp = findViewById(R.id.buttonVerify)
        layoutInput = findViewById(R.id.getOTPLayout)
        layoutVerify = findViewById(R.id.verifyOTPLayout)
    }

    override fun onConnected(bundle: Bundle?) {}
    override fun onConnectionSuspended(i: Int) {}
    override fun onOtpReceived(otp: String) {
        Toast.makeText(this, "Otp Received $otp", Toast.LENGTH_LONG).show()
        inputOtp.setText(otp)
    }

    override fun onOtpTimeout() {
        Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
    fun startSMSListener() {
        val mClient = SmsRetriever.getClient(this)
        val mTask = mClient.startSmsRetriever()
        mTask.addOnSuccessListener {
            layoutInput.visibility = View.GONE
            layoutVerify.visibility = View.VISIBLE
            Toast.makeText(this@MainActivity, "SMS Retriever starts", Toast.LENGTH_LONG).show()
        }
        mTask.addOnFailureListener {
            Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
        }
    }

    fun getHintPhoneNumber() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest)
        try {
            startIntentSenderForResult(mIntent.intentSender, RESOLVE_HINT, null, 0, 0, 0)
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                    // credential.getId();  <-- will need to process phone number string
                    inputMobileNumber.setText(credential!!.id)
                }
            }
        }
    }
}