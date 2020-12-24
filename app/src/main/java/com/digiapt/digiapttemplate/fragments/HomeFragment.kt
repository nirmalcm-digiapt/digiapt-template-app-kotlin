package com.digiapt.digiapttemplate.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.database.entities.Quote
import com.digiapt.digiapttemplate.models.QuoteItem
import com.digiapt.digiapttemplate.utils.Coroutine
import com.digiapt.digiapttemplate.viewmodelfactories.ContentsViewmodelFactory
import com.digiapt.digiapttemplate.viewmodels.ContentsViewModel
import com.explore.utils.view.hide
import com.explore.utils.view.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_quotes.*
import okhttp3.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment : Fragment(), KodeinAware {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout._fragment_home, container, false)
//    }

    override val kodein by kodein()

    private val factory: ContentsViewmodelFactory by instance()

    private lateinit var viewModel: ContentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(ContentsViewModel::class.java)
        bindUI()
    }


    private fun bindUI() = Coroutine.main {
        progress_bar.show()
        viewModel.dummyContents.await().observe(this, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }
}
