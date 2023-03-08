package com.mertyazi.footballapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyazi.footballapp.databinding.FragmentSavedMatchesBinding
import com.mertyazi.footballapp.model.Matche
import com.mertyazi.footballapp.view.adapters.MatchesAdapter

class SavedMatchesFragment : BaseFragment() {

    private var _binding: FragmentSavedMatchesBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMatchesAdapter: MatchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedMatchesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()

        viewModel.getSavedMatches().observe(viewLifecycleOwner) { matches ->
            val sorted: MutableList<Matche> = if (matches.isNotEmpty()) {
                matches.sortedWith(compareBy { it.utcDate }) as MutableList<Matche>
            } else {
                mutableListOf()
            }
            mMatchesAdapter.differ.submitList(sorted)
        }
    }

    fun deleteMatch(match: Matche) {
        viewModel.deleteSavedMatch(match)
    }

    private fun setupRecycleView() {
        mMatchesAdapter = MatchesAdapter(this)
        binding.rvSavedMatches.adapter = mMatchesAdapter
        binding.rvSavedMatches.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}