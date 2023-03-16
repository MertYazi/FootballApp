package com.mertyazi.footballapp.matches_today.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.ItemMatchBinding
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState

class MatchesTodayAdapter(
    val onHomeEmblemClicked: (MatcheViewState) -> Unit,
    val onAwayEmblemClicked: (MatcheViewState) -> Unit
): RecyclerView.Adapter<MatchesTodayAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(matcheViewState: MatcheViewState) {
            val bind = ItemMatchBinding.bind(itemView)
            bind.apply {
                ivHomeTeamEmblem.loadImage(matcheViewState.homeTeam.crest)
                ivAwayTeamEmblem.loadImage(matcheViewState.awayTeam.crest)
                tvHomeTeamName.text = matcheViewState.homeTeam.name
                tvAwayTeamName.text = matcheViewState.awayTeam.name

                when (matcheViewState.status) {
                    Constants.TIMED -> {
                        tvMatchDate.text = matcheViewState.utcDate!!
                        tvHomeTeamScore.text = "-"
                        tvAwayTeamScore.text = "-"
                    }
                    Constants.IN_PLAY_API -> {
                        tvMatchDate.text = Constants.IN_PLAY
                        tvHomeTeamScore.text = matcheViewState.score.fullTime.home.toString()
                        tvAwayTeamScore.text = matcheViewState.score.fullTime.away.toString()
                    }
                    Constants.PAUSED -> {
                        tvMatchDate.text = Constants.FIRST_HALF
                        tvHomeTeamScore.text = matcheViewState.score.fullTime.home.toString()
                        tvAwayTeamScore.text = matcheViewState.score.fullTime.away.toString()
                    }
                    Constants.FINISHED -> {
                        tvMatchDate.text = Constants.FINISHED
                        tvHomeTeamScore.text = matcheViewState.score.fullTime.home.toString()
                        tvAwayTeamScore.text = matcheViewState.score.fullTime.away.toString()
                    }
                    Constants.POSTPONED -> {
                        tvMatchDate.text = Constants.POSTPONED
                        tvHomeTeamScore.text = "-"
                        tvAwayTeamScore.text = "-"
                    }
                    Constants.SCHEDULED -> {
                        tvMatchDate.text = matcheViewState.utcDate!!
                        tvHomeTeamScore.text = "-"
                        tvAwayTeamScore.text = "-"
                    }
                    else -> {
                        tvMatchDate.text = "-"
                        tvHomeTeamScore.text = "-"
                        tvAwayTeamScore.text = "-"
                    }
                }
                ivHomeTeamEmblem.setOnClickListener {
                    onHomeEmblemClicked.invoke(matcheViewState)
                }
                ivAwayTeamEmblem.setOnClickListener {
                    onAwayEmblemClicked.invoke(matcheViewState)
                }
            }
        }

        fun bind(payloads: MutableList<Any>) {
            val bind = ItemMatchBinding.bind(itemView)
            if (payloads.isNotEmpty()) {
                val item = payloads[0] as Bundle
                val status = item.getString(Constants.MATCH_STATUS)
                val homeScore = item.getInt(Constants.HOME_SCORE)
                val awayScore = item.getInt(Constants.AWAY_SCORE)
                bind.apply {
                    tvMatchDate.text = status
                    tvHomeTeamScore.text = homeScore.toString()
                    tvAwayTeamScore.text = awayScore.toString()
                }
            }
        }
    }

    private val differCallback = object: DiffUtil.ItemCallback<MatcheViewState>() {
        override fun areItemsTheSame(oldItem: MatcheViewState, newItem: MatcheViewState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatcheViewState, newItem: MatcheViewState): Boolean {
            return oldItem.status == newItem.status &&
                    oldItem.score.fullTime.home == newItem.score.fullTime.home &&
                    oldItem.score.fullTime.away == newItem.score.fullTime.away &&
                    oldItem == newItem
        }

        override fun getChangePayload(oldItem: MatcheViewState, newItem: MatcheViewState): Any? {
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
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.bind(payloads)
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}