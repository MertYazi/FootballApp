package com.mertyazi.footballapp.leagues.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mertyazi.footballapp.databinding.FragmentLeaguesBinding
import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaguesFragment : Fragment() {

    private var _binding: FragmentLeaguesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LeaguesViewModel by viewModels()

    private var mLeaguesAdapter = LeaguesAdapter(::onItemClicked)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaguesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.viewStateLeagues.observe(viewLifecycleOwner) { leagues ->
            when (leagues) {
                is LeaguesViewState.ContentLeagues -> {
                    binding.loader.visibility = View.GONE
                    val sorted: MutableList<CompetitionXViewState> =
                        leagues.leagues.sortedWith(compareBy { it.name }) as MutableList<CompetitionXViewState>
                    mLeaguesAdapter.differ.submitList(sorted)
                }
                is LeaguesViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is LeaguesViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
            }
        }

        viewModel.getLeagues()
    }

    private fun setupRecyclerView() {
        binding.rvLeagues.adapter = mLeaguesAdapter
        binding.rvLeagues.layoutManager = GridLayoutManager(activity, 2)
    }

    private fun onItemClicked(viewState: CompetitionXViewState) {
        findNavController().navigate(
            LeaguesFragmentDirections.actionLeaguesFragmentToLeagueTableFragment(
                viewState.code,
                viewState.name,
                viewState.emblem
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}