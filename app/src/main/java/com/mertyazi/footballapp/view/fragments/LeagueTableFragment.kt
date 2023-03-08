package com.mertyazi.footballapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentLeagueTableBinding
import com.mertyazi.footballapp.utils.Constants
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.adapters.TablesAdapter
import java.io.IOException

class LeagueTableFragment : BaseFragment() {

    private var _binding: FragmentLeagueTableBinding? = null
    private val binding get() = _binding!!
    private lateinit var mTablesAdapter: TablesAdapter
    private lateinit var mTablesAdapter2: TablesAdapter
    private lateinit var mTablesAdapter3: TablesAdapter
    private lateinit var mTablesAdapter4: TablesAdapter
    private lateinit var mTablesAdapter5: TablesAdapter
    private lateinit var mTablesAdapter6: TablesAdapter
    private lateinit var mTablesAdapter7: TablesAdapter
    private lateinit var mTablesAdapter8: TablesAdapter

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
                binding.ivLeagueEmblem.loadUrl(it.leagueEmblem)
                binding.tvLeagueTitle.text = it.leagueName
                (requireActivity() as AppCompatActivity).supportActionBar?.title = it.leagueName
                viewModel.getLeagueTable(it.leagueCode)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        viewModel.leagueTable.observe(viewLifecycleOwner) { standings ->
            if (standings[0].stage == Constants.GROUP_STAGE) {
                mTablesAdapter.differ.submitList(standings[0].table)
                mTablesAdapter2.differ.submitList(standings[1].table)
                mTablesAdapter3.differ.submitList(standings[2].table)
                mTablesAdapter4.differ.submitList(standings[3].table)
                mTablesAdapter5.differ.submitList(standings[4].table)
                mTablesAdapter6.differ.submitList(standings[5].table)
                mTablesAdapter7.differ.submitList(standings[6].table)
                mTablesAdapter8.differ.submitList(standings[7].table)

                binding.rvTables2.visibility = View.VISIBLE
                binding.rvTables3.visibility = View.VISIBLE
                binding.rvTables4.visibility = View.VISIBLE
                binding.rvTables5.visibility = View.VISIBLE
                binding.rvTables6.visibility = View.VISIBLE
                binding.rvTables7.visibility = View.VISIBLE
                binding.rvTables8.visibility = View.VISIBLE
            } else {
                mTablesAdapter.differ.submitList(standings[0].table)
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
        mTablesAdapter = TablesAdapter(this)
        mTablesAdapter2 = TablesAdapter(this)
        mTablesAdapter3 = TablesAdapter(this)
        mTablesAdapter4 = TablesAdapter(this)
        mTablesAdapter5 = TablesAdapter(this)
        mTablesAdapter6 = TablesAdapter(this)
        mTablesAdapter7 = TablesAdapter(this)
        mTablesAdapter8 = TablesAdapter(this)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}