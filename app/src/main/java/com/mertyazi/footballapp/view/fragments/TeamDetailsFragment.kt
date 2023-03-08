package com.mertyazi.footballapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentTeamDetailsBinding
import com.mertyazi.footballapp.model.Matche
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.adapters.MatchesAdapter
import java.io.IOException

class TeamDetailsFragment : BaseFragment() {

    private var _binding: FragmentTeamDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMatchesAdapter: MatchesAdapter

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
                binding.ivTeamEmblem.loadUrl(it.teamEmblem)
                binding.tvTeamName.text = it.teamName
                (requireActivity() as AppCompatActivity).supportActionBar?.title =
                    it.teamName + " " + resources.getString(R.string.matches)
                viewModel.getMatches(it.teamId)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        viewModel.matches.observe(viewLifecycleOwner) { matches ->
            mMatchesAdapter.differ.submitList(matches)
        }
    }

    fun saveMatch(match: Matche) {
        viewModel.saveMatch(match)
        Toast.makeText(requireActivity(), resources.getString(R.string.match_saved), Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView() {
        mMatchesAdapter = MatchesAdapter(this)
        binding.rvTeamDetails.adapter = mMatchesAdapter
        binding.rvTeamDetails.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}