package com.digiapt.digiapttemplate.listeners

interface OtpReceivedInterface {
    fun onOtpReceived(otp: String)
    fun onOtpTimeout()
}