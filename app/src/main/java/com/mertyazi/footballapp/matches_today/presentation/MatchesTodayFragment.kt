package com.mertyazi.footballapp.matches_today.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jama.carouselview.enums.OffsetType
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentMatchesTodayBinding
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.databinding.ItemLiveMatchBinding
import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesTodayFragment : Fragment() {

    private var _binding: FragmentMatchesTodayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MatchesTodayViewModel by viewModels()

    private var mMatchesTodayAdapter =
        MatchesTodayAdapter(::onHomeEmblemClicked, ::onAwayEmblemClicked)
    private var mMatchesTodayLeaguesAdapter = MatchesTodayLeaguesAdapter(::onLeagueClicked)
    private var mAvailableMatches = ArrayList<MatcheViewState>()
    private var mAvailableLeagues = ArrayList<String>()
    private var mLiveMatches = ArrayList<MatcheViewState>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        viewModel.viewStateMatchesToday.observe(viewLifecycleOwner) { matches ->
            when (matches) {
                is MatchesTodayViewState.ContentMatchesToday -> {
                    binding.loader.visibility = View.GONE
                    mLiveMatches = ArrayList()
                    mAvailableLeagues = ArrayList()
                    mAvailableMatches = ArrayList()
                    for (match in matches.matchesToday.matches) {
                        mAvailableMatches.add(match)
                        mAvailableLeagues.add(match.competition.name)
                        if (match.status == Constants.IN_PLAY_API || match.status == Constants.PAUSED) {
                            mLiveMatches.add(match)
                        }
                    }

                    val sortedLive: MutableList<MatcheViewState> = if (mLiveMatches.isNotEmpty()) {
                        mLiveMatches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name})) as MutableList<MatcheViewState>
                    } else {
                        mutableListOf()
                    }

                    viewModel.getLeagues(mAvailableLeagues)

                    if (mLiveMatches.size > 0) {
                        binding.tvLiveMatchesToday.visibility = View.VISIBLE
                        binding.cvLiveMatchesToday.visibility = View.VISIBLE
                    }

                    val sorted: MutableList<MatcheViewState> = if (matches.matchesToday.matches.isNotEmpty()) {
                        matches.matchesToday.matches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name}))
                                as MutableList<MatcheViewState>
                    } else {
                        mutableListOf()
                    }
                    mMatchesTodayAdapter.differ.submitList(sorted)

                    binding.cvLiveMatchesToday.apply {
                        size = sortedLive.size
                        resource = R.layout.item_live_match
                        hideIndicator(true)
                        carouselOffset = OffsetType.START
                        setCarouselViewListener { view, position ->
                            val bindingLiveMatches: ItemLiveMatchBinding = ItemLiveMatchBinding.bind(view)
                            bindingLiveMatches.ivHomeLiveMatch.loadImage(
                                sortedLive[position].homeTeam.crest)

                            bindingLiveMatches.ivAwayLiveMatch.loadImage(
                                sortedLive[position].awayTeam.crest)

                            bindingLiveMatches.tvHomeNameLiveMatch.text =
                                sortedLive[position].homeTeam.name

                            bindingLiveMatches.tvAwayNameLiveMatch.text =
                                sortedLive[position].awayTeam.name

                            bindingLiveMatches.tvHomeScoreLiveMatch.text =
                                sortedLive[position].score.fullTime.home.toString()

                            bindingLiveMatches.tvAwayScoreLiveMatch.text =
                                sortedLive[position].score.fullTime.away.toString()

                            bindingLiveMatches.ivHomeLiveMatch.setOnClickListener {
                                onHomeEmblemClicked(sortedLive[position])
                            }

                            bindingLiveMatches.ivAwayLiveMatch.setOnClickListener {
                                onAwayEmblemClicked(sortedLive[position])
                            }
                        }
                        show()
                    }
                }
                is MatchesTodayViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is MatchesTodayViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
            }
        }

        viewModel.viewStateLeagues.observe(viewLifecycleOwner) { leagues ->
            when (leagues) {
                is MatchesTodayViewState.ContentLeagues -> {
                    binding.loader.visibility = View.GONE
                    val sorted: MutableList<CompetitionXViewState> =
                        leagues.leagues.sortedWith(compareBy { it.name }) as MutableList<CompetitionXViewState>
                    mMatchesTodayLeaguesAdapter.leaguesList(sorted)
                }
                is MatchesTodayViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is MatchesTodayViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
            }
        }

        viewModel.getMatchesToday()
    }

    private fun setupRecyclerViews() {
        binding.rvMatchesToday.adapter = mMatchesTodayAdapter
        binding.rvMatchesToday.layoutManager = LinearLayoutManager(activity)

        binding.rvLeaguesMatchesToday.adapter = mMatchesTodayLeaguesAdapter
        binding.rvLeaguesMatchesToday.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun onLeagueClicked(viewState: CompetitionXViewState) {
        val sorted: MutableList<MatcheViewState>
        if (mAvailableMatches.isNotEmpty()) {
            when (viewState.name) {
                Constants.ALL_LEAGUES -> {
                    sorted = mAvailableMatches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name}))
                            as MutableList<MatcheViewState>
                }
                else -> {
                    val filteredList = mutableListOf<MatcheViewState>()
                    for (match in mAvailableMatches) {
                        if (match.competition.name == viewState.name) {
                            filteredList.add(match)
                        }
                    }
                    sorted = filteredList.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name}))
                            as MutableList<MatcheViewState>
                }
            }
            mMatchesTodayAdapter.differ.submitList(sorted)
        }
    }

    private fun onHomeEmblemClicked(viewState: MatcheViewState) {
        findNavController().navigate(
            MatchesTodayFragmentDirections.actionMatchesTodayFragmentToTeamDetailsFragment(
                viewState.homeTeam.id,
                viewState.homeTeam.name,
                viewState.homeTeam.crest
            )
        )
    }

    private fun onAwayEmblemClicked(viewState: MatcheViewState) {
        findNavController().navigate(
            MatchesTodayFragmentDirections.actionMatchesTodayFragmentToTeamDetailsFragment(
                viewState.awayTeam.id,
                viewState.awayTeam.name,
                viewState.awayTeam.crest
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}