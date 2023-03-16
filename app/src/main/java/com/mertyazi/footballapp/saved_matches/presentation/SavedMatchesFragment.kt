package com.mertyazi.footballapp.saved_matches.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentSavedMatchesBinding
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedMatchesFragment : Fragment() {

    private var _binding: FragmentSavedMatchesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SavedMatchesViewModel by viewModels()

    private var mMatchesAdapter = SavedMatchesAdapter(
        ::onHomeEmblemClicked, ::onAwayEmblemClicked, ::onDeleteClicked)

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

        viewModel.viewStateSavedMatches.observe(viewLifecycleOwner) { matches ->
            when (matches) {
                is SavedMatchesViewState.ContentSavedMatches -> {
                    binding.loader.visibility = View.GONE
                    val sorted: MutableList<MatcheViewState> = if (matches.matches.isNotEmpty()) {
                        matches.matches.sortedWith(compareBy { it.utcDate }) as MutableList<MatcheViewState>
                    } else {
                        mutableListOf()
                    }
                    mMatchesAdapter.differ.submitList(sorted)
                }
                is SavedMatchesViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is SavedMatchesViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
            }
        }

        viewModel.getSavedMatches()
    }

    private fun setupRecycleView() {
        binding.rvSavedMatches.adapter = mMatchesAdapter
        binding.rvSavedMatches.layoutManager = LinearLayoutManager(activity)
    }

    private fun onHomeEmblemClicked(viewState: MatcheViewState) {
        findNavController().navigate(
            SavedMatchesFragmentDirections.actionSavedMatchesFragmentToTeamDetailsFragment(
                viewState.homeTeam.id,
                viewState.homeTeam.name,
                viewState.homeTeam.crest
            )
        )
    }

    private fun onAwayEmblemClicked(viewState: MatcheViewState) {
        findNavController().navigate(
            SavedMatchesFragmentDirections.actionSavedMatchesFragmentToTeamDetailsFragment(
                viewState.awayTeam.id,
                viewState.awayTeam.name,
                viewState.awayTeam.crest
            )
        )
    }

    private fun onDeleteClicked(viewState: MatcheViewState) {
        viewModel.deleteMatchClicked(viewState)
        Toast.makeText(requireActivity(), resources.getString(R.string.match_deleted), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}