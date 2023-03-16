package com.mertyazi.footballapp.team_details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentTeamDetailsBinding
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {

    private var _binding: FragmentTeamDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TeamDetailsViewModel by viewModels()

    private var mMatchesAdapter = TeamDetailsAdapter(
        ::onHomeEmblemClicked, ::onAwayEmblemClicked, ::onSaveClicked)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val args: TeamDetailsFragmentArgs by navArgs()
        args.let {
            try {
                binding.ivTeamEmblem.loadImage(it.teamEmblem)
                binding.tvTeamName.text = it.teamName
                viewModel.getMatchDetails(it.teamId)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        viewModel.viewStateTeamDetails.observe(viewLifecycleOwner) { matches ->
            when (matches) {
                is TeamDetailsViewState.ContentTeamDetails -> {
                    binding.loader.visibility = View.GONE
                    mMatchesAdapter.differ.submitList(matches.matches)
                }
                is TeamDetailsViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is TeamDetailsViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTeamDetails.adapter = mMatchesAdapter
        binding.rvTeamDetails.layoutManager = LinearLayoutManager(activity)
    }

    private fun onHomeEmblemClicked(viewState: MatcheViewState) {
        findNavController().navigate(
            TeamDetailsFragmentDirections.actionTeamDetailsFragmentSelf(
                viewState.homeTeam.id,
                viewState.homeTeam.name,
                viewState.homeTeam.crest
            )
        )
    }

    private fun onAwayEmblemClicked(viewState: MatcheViewState) {
        findNavController().navigate(
            TeamDetailsFragmentDirections.actionTeamDetailsFragmentSelf(
                viewState.awayTeam.id,
                viewState.awayTeam.name,
                viewState.awayTeam.crest
            )
        )
    }

    private fun onSaveClicked(viewState: MatcheViewState) {
        viewModel.saveMatchClicked(viewState)
        Toast.makeText(requireActivity(), resources.getString(R.string.match_saved), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}