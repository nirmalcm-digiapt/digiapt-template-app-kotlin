package com.digiapt.digiapttemplate.listeners

import android.view.View
import com.digiapt.digiapttemplate.models.ContentModel

interface SectionClickListener {
    fun onSectionItemClick(view: View, contentModel: ContentModel)
}