package com.digiapt.digiapttemplate.viewmodels

import androidx.lifecycle.ViewModel;
import com.digiapt.digiapttemplate.repositories.UserRepository

class ProfileViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.getUser()

}