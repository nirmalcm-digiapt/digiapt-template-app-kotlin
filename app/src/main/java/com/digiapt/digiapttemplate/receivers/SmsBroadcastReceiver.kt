package com.digiapt.digiapttemplate.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.digiapt.digiapttemplate.listeners.OtpReceivedInterface

import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

/**
 * Created on : May 21, 2019
 * Author     : AndroidWave
 */
class SmsBroadcastReceiver : BroadcastReceiver() {
    internal var otpReceiveInterface: OtpReceivedInterface? = null
    fun setOnOtpListeners(otpReceiveInterface: OtpReceivedInterface) {
        this.otpReceiveInterface = otpReceiveInterface
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val mStatus = extras!!.get(SmsRetriever.EXTRA_STATUS) as Status?
            when (mStatus!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    // Get SMS message contents'
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String?
                    Log.d(TAG, "onReceive: failure " + message!!)
                    if (otpReceiveInterface != null) {
                        val otp = message.replace("<#> Your otp code is : ", "")
                        otpReceiveInterface!!.onOtpReceived(otp)
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    // Waiting for SMS timed out (5 minutes)
                    Log.d(TAG, "onReceive: failure")
                    if (otpReceiveInterface != null) {
                        otpReceiveInterface!!.onOtpTimeout()
                    }
                }
            }
        }
    }

    companion object {
        private val TAG = "SmsBroadcastReceiver"
    }
}