package com.mertyazi.footballapp.matches_today.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.databinding.ItemFilterMatchBinding
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage

class MatchesTodayLeaguesAdapter(
    val onLeagueClicked: (CompetitionXViewState) -> Unit
): RecyclerView.Adapter<MatchesTodayLeaguesAdapter.ViewHolder>() {

    private var leagues: MutableList<CompetitionXViewState> = mutableListOf()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(competitionXViewState: CompetitionXViewState) {
            val bind = ItemFilterMatchBinding.bind(itemView)
            itemView.setOnClickListener {
                onLeagueClicked(competitionXViewState)
            }
            bind.apply {
                tvFilterMatch.text = competitionXViewState.name
                ivFilterMatch.loadImage(competitionXViewState.emblem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filter_match, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(leagues[position])
    }

    override fun getItemCount(): Int {
        return leagues.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun leaguesList(list: MutableList<CompetitionXViewState>) {
        leagues = list
        notifyDataSetChanged()
    }
}