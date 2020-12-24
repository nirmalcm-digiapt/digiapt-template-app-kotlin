package com.digiapt.digiapttemplate.trash

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.digiapt.digiapttemplate.R
import com.digiapt.digiapttemplate.databinding.FragmentProfileBinding
import com.digiapt.digiapttemplate.utils.LanguageUtils.Companion.changeLanguage
import com.digiapt.digiapttemplate.viewmodelfactories.ProfileViewModelFactory
import com.digiapt.digiapttemplate.viewmodels.ProfileViewModel
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: ProfileViewModel
    private val factory: ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        changeLanguage(context!!,"hi")
        return binding.root
    }
}