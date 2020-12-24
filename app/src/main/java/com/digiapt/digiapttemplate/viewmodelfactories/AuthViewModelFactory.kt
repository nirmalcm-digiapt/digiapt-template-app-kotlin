package com.digiapt.digiapttemplate.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digiapt.digiapttemplate.repositories.UserRepository
import com.digiapt.digiapttemplate.viewmodels.AuthViewModel

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}