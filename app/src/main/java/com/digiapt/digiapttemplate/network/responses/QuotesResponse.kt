package com.digiapt.digiapttemplate.network.responses

import com.digiapt.digiapttemplate.database.entities.Quote

data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)