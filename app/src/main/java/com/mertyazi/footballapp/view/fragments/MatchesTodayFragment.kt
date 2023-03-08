package com.mertyazi.footballapp.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jama.carouselview.enums.OffsetType
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.FragmentMatchesTodayBinding
import com.mertyazi.footballapp.model.CompetitionX
import com.mertyazi.footballapp.model.Matche
import com.mertyazi.footballapp.utils.Constants
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.adapters.FiltersAdapter
import com.mertyazi.footballapp.view.adapters.MatchesAdapter

class MatchesTodayFragment : BaseFragment() {

    private var _binding: FragmentMatchesTodayBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMatchesAdapter: MatchesAdapter
    private lateinit var mFilterAdapter: FiltersAdapter
    private var mAvailableMatches = ArrayList<Matche>()
    private var mAvailableLeagues = ArrayList<String>()
    private var mLiveMatches = ArrayList<Matche>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        viewModel.matchesToday.observe(viewLifecycleOwner) { matches ->
            mLiveMatches = ArrayList()
            mAvailableLeagues = ArrayList()
            for (match in matches) {
                mAvailableLeagues.add(match.competition.name)
                if (match.status == Constants.IN_PLAY_API || match.status == Constants.PAUSED) {
                    mLiveMatches.add(match)
                }
            }

            val sortedLive: MutableList<Matche> = if (mLiveMatches.isNotEmpty()) {
                mLiveMatches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name})) as MutableList<Matche>
            } else {
                mutableListOf()
            }

            viewModel.getLeagues(mAvailableLeagues, Constants.ALL_LEAGUES)

            if (mLiveMatches.size > 0) {
                binding.tvLiveMatchesToday.visibility = View.VISIBLE
                binding.cvLiveMatchesToday.visibility = View.VISIBLE
            }

            val sorted: MutableList<Matche> = if (matches.isNotEmpty()) {
                matches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name})) as MutableList<Matche>
            } else {
                mutableListOf()
            }
            mMatchesAdapter.differ.submitList(sorted)

            binding.cvLiveMatchesToday.apply {
                size = sortedLive.size
                resource = R.layout.item_live_match
                hideIndicator(true)
                //autoPlay = true
                //indicatorAnimationType = IndicatorAnimationType.THIN_WORM
                carouselOffset = OffsetType.START
                setCarouselViewListener { view, position ->
                    val imageHome = view.findViewById<ImageView>(R.id.iv_home_live_match)
                    imageHome.loadUrl(sortedLive[position].homeTeam.crest)

                    val imageAway = view.findViewById<ImageView>(R.id.iv_away_live_match)
                    imageAway.loadUrl(sortedLive[position].awayTeam.crest)

                    val textHome = view.findViewById<TextView>(R.id.tv_home_name_live_match)
                    textHome.text = sortedLive[position].homeTeam.name

                    val textAway = view.findViewById<TextView>(R.id.tv_away_name_live_match)
                    textAway.text = sortedLive[position].awayTeam.name

                    val textHomeScore = view.findViewById<TextView>(R.id.tv_home_score_live_match)
                    textHomeScore.text = sortedLive[position].score.fullTime.home.toString()

                    val textAwayScore = view.findViewById<TextView>(R.id.tv_away_score_live_match)
                    textAwayScore.text = sortedLive[position].score.fullTime.away.toString()
                }
                show()
            }
        }

        viewModel.leagues.observe(viewLifecycleOwner) { leagues ->
            val sorted: MutableList<CompetitionX> = leagues.sortedWith(compareBy { it.name }) as MutableList<CompetitionX>
            mFilterAdapter.leaguesList(sorted)
        }

    }

    private fun setupRecyclerViews() {
        mMatchesAdapter = MatchesAdapter(this)
        binding.rvMatchesToday.adapter = mMatchesAdapter
        binding.rvMatchesToday.layoutManager = LinearLayoutManager(activity)
        //binding.rvMatchesToday.itemAnimator = null

        mFilterAdapter = FiltersAdapter(this@MatchesTodayFragment)
        binding.rvLeaguesMatchesToday.adapter = mFilterAdapter
        binding.rvLeaguesMatchesToday.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    fun filterSelection(filter: CompetitionX) {
        viewModel.matchesToday.observe(viewLifecycleOwner) { matches ->
            mAvailableMatches = ArrayList()
            for (match in matches) {
                if (match.competition.name == filter.name) {
                    mAvailableMatches.add(match)
                }
            }
            val sorted: MutableList<Matche>
            if (matches.isNotEmpty()) {
                when (filter.name) {
                    Constants.ALL_LEAGUES -> {
                        sorted = matches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name})) as MutableList<Matche>
                    }
                    else -> {
                        sorted = mAvailableMatches.sortedWith(compareBy({it.utcDate}, {it.homeTeam.name})) as MutableList<Matche>
                    }
                }
                mMatchesAdapter.differ.submitList(sorted)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}