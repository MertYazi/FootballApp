package com.mertyazi.footballapp.league_table.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentLeagueTableBinding
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class LeagueTableFragment : Fragment() {

    private var _binding: FragmentLeagueTableBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LeagueTableViewModel by viewModels()

    private val mTablesAdapter = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter2 = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter3 = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter4 = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter5 = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter6 = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter7 = LeagueTableAdapter(::onItemClicked)
    private var mTablesAdapter8 = LeagueTableAdapter(::onItemClicked)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeagueTableBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val args: LeagueTableFragmentArgs by navArgs()
        args.let {
            try {
                binding.ivLeagueEmblem.loadImage(it.leagueEmblem)
                binding.tvLeagueTitle.text = it.leagueName
                viewModel.getLeagueTable(it.leagueCode)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        viewModel.viewStateLeagueTable.observe(viewLifecycleOwner) { standings ->
            when (standings) {
                is LeagueTableViewState.ContentLeagueTable -> {
                    binding.loader.visibility = View.GONE
                    if (standings.standings[0].stage == Constants.GROUP_STAGE) {
                        mTablesAdapter.differ.submitList(standings.standings[0].table)
                        mTablesAdapter2.differ.submitList(standings.standings[1].table)
                        mTablesAdapter3.differ.submitList(standings.standings[2].table)
                        mTablesAdapter4.differ.submitList(standings.standings[3].table)
                        mTablesAdapter5.differ.submitList(standings.standings[4].table)
                        mTablesAdapter6.differ.submitList(standings.standings[5].table)
                        mTablesAdapter7.differ.submitList(standings.standings[6].table)
                        mTablesAdapter8.differ.submitList(standings.standings[7].table)

                        binding.rvTables2.visibility = View.VISIBLE
                        binding.rvTables3.visibility = View.VISIBLE
                        binding.rvTables4.visibility = View.VISIBLE
                        binding.rvTables5.visibility = View.VISIBLE
                        binding.rvTables6.visibility = View.VISIBLE
                        binding.rvTables7.visibility = View.VISIBLE
                        binding.rvTables8.visibility = View.VISIBLE
                    } else {
                        mTablesAdapter.differ.submitList(standings.standings[0].table)
                    }
                }
                is LeagueTableViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is LeagueTableViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
            }
        }

        binding.tvName.text = resources.getString(R.string.teams)
        binding.tvPlayed.text = resources.getString(R.string.games_played)
        binding.tvWon.text = resources.getString(R.string.won)
        binding.tvDrawn.text = resources.getString(R.string.drawn)
        binding.tvLost.text = resources.getString(R.string.lost)
        binding.tvPoints.text = resources.getString(R.string.points)
    }

    private fun setupRecyclerView() {
        binding.rvTables.adapter = mTablesAdapter
        binding.rvTables.layoutManager = LinearLayoutManager(activity)
        binding.rvTables2.adapter = mTablesAdapter2
        binding.rvTables2.layoutManager = LinearLayoutManager(activity)
        binding.rvTables3.adapter = mTablesAdapter3
        binding.rvTables3.layoutManager = LinearLayoutManager(activity)
        binding.rvTables4.adapter = mTablesAdapter4
        binding.rvTables4.layoutManager = LinearLayoutManager(activity)
        binding.rvTables5.adapter = mTablesAdapter5
        binding.rvTables5.layoutManager = LinearLayoutManager(activity)
        binding.rvTables6.adapter = mTablesAdapter6
        binding.rvTables6.layoutManager = LinearLayoutManager(activity)
        binding.rvTables7.adapter = mTablesAdapter7
        binding.rvTables7.layoutManager = LinearLayoutManager(activity)
        binding.rvTables8.adapter = mTablesAdapter8
        binding.rvTables8.layoutManager = LinearLayoutManager(activity)
    }

    private fun onItemClicked(viewState: TableViewState) {
        findNavController().navigate(
            LeagueTableFragmentDirections.actionLeagueTableFragmentToTeamDetailsFragment(
                viewState.team.id,
                viewState.team.name,
                viewState.team.crest
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}