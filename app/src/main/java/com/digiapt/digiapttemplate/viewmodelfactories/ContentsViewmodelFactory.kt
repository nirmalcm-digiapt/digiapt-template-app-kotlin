package com.digiapt.digiapttemplate.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digiapt.digiapttemplate.repositories.ContentsRepository
import com.digiapt.digiapttemplate.viewmodels.ContentsViewModel

@Suppress("UNCHECKED_CAST")
class ContentsViewmodelFactory(
    private val repository: ContentsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContentsViewModel(repository) as T
    }
}