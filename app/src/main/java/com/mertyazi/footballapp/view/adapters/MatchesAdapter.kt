package com.mertyazi.footballapp.view.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.ItemMatchBinding
import com.mertyazi.footballapp.model.Matche
import com.mertyazi.footballapp.utils.Constants
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.fragments.MatchesTodayFragment
import com.mertyazi.footballapp.view.fragments.MatchesTodayFragmentDirections
import com.mertyazi.footballapp.view.fragments.SavedMatchesFragment
import com.mertyazi.footballapp.view.fragments.SavedMatchesFragmentDirections
import com.mertyazi.footballapp.view.fragments.TeamDetailsFragment

class MatchesAdapter(private val fragment: Fragment): RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    class ViewHolder(view: ItemMatchBinding): RecyclerView.ViewHolder(view.root) {
        val tvMatchTime = view.tvMatchDate
        val ivHomeTeamImage = view.ivHomeTeamEmblem
        val tvHomeTeamScore = view.tvHomeTeamScore
        val tvHomeTeamName = view.tvHomeTeamName
        val ivAwayTeamImage = view.ivAwayTeamEmblem
        val tvAwayTeamScore = view.tvAwayTeamScore
        val tvAwayTeamName = view.tvAwayTeamName
        val btnSaveMatch = view.tvSaveMatch
    }

    private val differCallback = object: DiffUtil.ItemCallback<Matche>() {
        override fun areItemsTheSame(oldItem: Matche, newItem: Matche): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Matche, newItem: Matche): Boolean {
            return oldItem.status == newItem.status &&
                    oldItem.score.fullTime.home == newItem.score.fullTime.home &&
                    oldItem.score.fullTime.away == newItem.score.fullTime.away &&
                    oldItem == newItem
        }

        override fun getChangePayload(oldItem: Matche, newItem: Matche): Any? {
            val bundle: Bundle = bundleOf()
            if (oldItem.status != newItem.status) {
                bundle.apply {
                    putString(Constants.MATCH_STATUS, newItem.status)
                }
            }
            if (oldItem.score.fullTime.home != newItem.score.fullTime.home) {
                bundle.apply {
                    putInt(Constants.HOME_SCORE, newItem.score.fullTime.home)
                }
            }
            if (oldItem.score.fullTime.away != newItem.score.fullTime.away) {
                bundle.apply {
                    putInt(Constants.AWAY_SCORE, newItem.score.fullTime.away)
                }
            }
            if (bundle.size() == 0) {
                return null
            }
            return bundle
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMatchBinding = ItemMatchBinding.inflate(
            LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = differ.currentList[position]
        holder.apply {
            ivHomeTeamImage.loadUrl(match.homeTeam.crest)
            ivAwayTeamImage.loadUrl(match.awayTeam.crest)
            tvHomeTeamName.text = match.homeTeam.name
            tvAwayTeamName.text = match.awayTeam.name

            when (match.status) {
                Constants.TIMED -> {
                    when (fragment) {
                        is MatchesTodayFragment -> {
                            tvMatchTime.text = Constants.toTimeForTR(match.utcDate!!, Constants.TODAY)
                        } else -> {
                            tvMatchTime.text = Constants.toTimeForTR(match.utcDate!!, "")
                        }
                    }
                    tvHomeTeamScore.text = "-"
                    tvAwayTeamScore.text = "-"
                }
                Constants.IN_PLAY_API -> {
                    tvMatchTime.text = Constants.IN_PLAY
                    tvHomeTeamScore.text = match.score.fullTime.home.toString()
                    tvAwayTeamScore.text = match.score.fullTime.away.toString()
                }
                Constants.PAUSED -> {
                    tvMatchTime.text = Constants.FIRST_HALF
                    tvHomeTeamScore.text = match.score.fullTime.home.toString()
                    tvAwayTeamScore.text = match.score.fullTime.away.toString()
                }
                Constants.FINISHED -> {
                    tvMatchTime.text = Constants.FINISHED
                    tvHomeTeamScore.text = match.score.fullTime.home.toString()
                    tvAwayTeamScore.text = match.score.fullTime.away.toString()
                }
                Constants.POSTPONED -> {
                    tvMatchTime.text = Constants.POSTPONED
                    tvHomeTeamScore.text = "-"
                    tvAwayTeamScore.text = "-"
                }
                Constants.SCHEDULED -> {
                    when (fragment) {
                        is MatchesTodayFragment -> {
                            tvMatchTime.text = Constants.toTimeForTR(match.utcDate!!, Constants.TODAY)
                        } else -> {
                            tvMatchTime.text = Constants.toTimeForTR(match.utcDate!!, "")
                        }
                    }
                    tvHomeTeamScore.text = "-"
                    tvAwayTeamScore.text = "-"
                }
                else -> {
                    tvMatchTime.text = "-"
                    tvHomeTeamScore.text = "-"
                    tvAwayTeamScore.text = "-"
                }
            }
            when (fragment) {
                is MatchesTodayFragment -> {
                    ivHomeTeamImage.setOnClickListener {
                        fragment.findNavController().navigate(
                            MatchesTodayFragmentDirections.actionMatchesTodayFragmentToTeamDetailsFragment(
                                match.homeTeam.id,
                                match.homeTeam.name,
                                match.homeTeam.crest
                            )
                        )
                    }
                    ivAwayTeamImage.setOnClickListener {
                        fragment.findNavController().navigate(
                            MatchesTodayFragmentDirections.actionMatchesTodayFragmentToTeamDetailsFragment(
                                match.awayTeam.id,
                                match.awayTeam.name,
                                match.awayTeam.crest
                            )
                        )
                    }
                }
                is TeamDetailsFragment -> {
                    btnSaveMatch.visibility = View.VISIBLE
                    btnSaveMatch.text = fragment.resources.getString(R.string.save)
                    btnSaveMatch.setOnClickListener {
                        fragment.saveMatch(match)
                    }
                }
                is SavedMatchesFragment -> {
                    btnSaveMatch.visibility = View.VISIBLE
                    btnSaveMatch.text = fragment.resources.getString(R.string.delete)
                    btnSaveMatch.setOnClickListener {
                        fragment.deleteMatch(match)
                    }
                    ivHomeTeamImage.setOnClickListener {
                        fragment.findNavController().navigate(
                            SavedMatchesFragmentDirections.actionSavedMatchesFragmentToTeamDetailsFragment(
                                match.homeTeam.id,
                                match.homeTeam.name,
                                match.homeTeam.crest
                            )
                        )
                    }
                    ivAwayTeamImage.setOnClickListener {
                        fragment.findNavController().navigate(
                            SavedMatchesFragmentDirections.actionSavedMatchesFragmentToTeamDetailsFragment(
                                match.awayTeam.id,
                                match.awayTeam.name,
                                match.awayTeam.crest
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = payloads[0] as Bundle
            val status = item.getString(Constants.MATCH_STATUS)
            val homeScore = item.getInt(Constants.HOME_SCORE)
            val awayScore = item.getInt(Constants.AWAY_SCORE)
            holder.apply {
                tvMatchTime.text = status
                tvHomeTeamScore.text = homeScore.toString()
                tvAwayTeamScore.text = awayScore.toString()
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}