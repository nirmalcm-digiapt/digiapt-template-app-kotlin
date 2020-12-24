package com.digiapt.digiapttemplate.models

import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.database.entities.Quote
import com.digiapt.digiapttemplate.databinding.AdapterQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<AdapterQuoteBinding>(){

    override fun getLayout() = R.layout.adapter_quote

    override fun bind(viewBinding: AdapterQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}