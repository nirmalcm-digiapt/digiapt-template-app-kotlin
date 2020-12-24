package com.digiapt.digiapttemplate.repositories

import com.digiapt.digiapttemplate.database.AppDatabase
import com.digiapt.digiapttemplate.database.entities.User
import com.digiapt.digiapttemplate.network.Api
import com.explore.utils.network.NetworkSafeApiRequest
import com.digiapt.digiapttemplate.network.responses.AuthResponse

class UserRepository(private val api: Api, private val db: AppDatabase) : NetworkSafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) : AuthResponse {
        return apiRequest{ api.userSignup(name, email, password)}
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

}