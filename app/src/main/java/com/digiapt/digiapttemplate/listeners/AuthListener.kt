package com.digiapt.digiapttemplate.listeners

import com.digiapt.digiapttemplate.database.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}