package com.mertyazi.footballapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mertyazi.footballapp.databinding.FragmentLeaguesBinding
import com.mertyazi.footballapp.model.CompetitionX
import com.mertyazi.footballapp.view.adapters.LeaguesAdapter

class LeaguesFragment : BaseFragment() {

    private var _binding: FragmentLeaguesBinding? = null
    private val binding get() = _binding!!
    private lateinit var mLeaguesAdapter: LeaguesAdapter

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

        viewModel.leagues.observe(viewLifecycleOwner) { leagues ->
            val sorted: MutableList<CompetitionX> = leagues.sortedWith(compareBy { it.name }) as MutableList<CompetitionX>
            mLeaguesAdapter.differ.submitList(sorted)
        }

        viewModel.getLeagues(arrayListOf(), "")
    }

    private fun setupRecyclerView() {
        mLeaguesAdapter = LeaguesAdapter(this)
        binding.rvLeagues.adapter = mLeaguesAdapter
        binding.rvLeagues.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}