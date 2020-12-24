package com.digiapt.digiapttemplate.viewmodels

import androidx.lifecycle.ViewModel;
import com.digiapt.digiapttemplate.repositories.ContentsRepository
import com.digiapt.digiapttemplate.utils.lazyDeferred

class ContentsViewModel(
    contentsRepository: ContentsRepository
) : ViewModel() {

    val dummyContents by lazyDeferred {
        contentsRepository.getQuotes()
    }
}
