package com.mertyazi.footballapp.saved_matches.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.ItemMatchBinding
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState

class SavedMatchesAdapter(
    val onHomeEmblemClicked: (MatcheViewState) -> Unit,
    val onAwayEmblemClicked: (MatcheViewState) -> Unit,
    val onDeleteClicked: (MatcheViewState) -> Unit,
): RecyclerView.Adapter<SavedMatchesAdapter.ViewHolder>() {

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
                tvSaveMatch.visibility = View.VISIBLE
                tvSaveMatch.text = itemView.resources.getString(R.string.delete)
                tvSaveMatch.setOnClickListener {
                    onDeleteClicked.invoke(matcheViewState)
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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}