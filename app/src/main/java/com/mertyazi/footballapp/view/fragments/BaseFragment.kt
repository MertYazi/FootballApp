package com.mertyazi.footballapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.application.MatchesApplication
import com.mertyazi.footballapp.viewmodel.MatchesViewModel
import com.mertyazi.footballapp.viewmodel.MatchesViewModelFactory

open class BaseFragment : Fragment() {

    val viewModel: MatchesViewModel by viewModels {
        MatchesViewModelFactory(requireActivity().application, (requireActivity().application as MatchesApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

}