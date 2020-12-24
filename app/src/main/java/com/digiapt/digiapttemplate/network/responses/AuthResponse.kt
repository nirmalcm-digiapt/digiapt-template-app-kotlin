package com.digiapt.digiapttemplate.network.responses

import com.digiapt.digiapttemplate.database.entities.User

data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)