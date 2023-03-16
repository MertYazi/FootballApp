package com.mertyazi.footballapp.leagues.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.databinding.ItemLeaguesBinding
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage
import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState

class LeaguesAdapter(
    val onItemClicked: (CompetitionXViewState) -> Unit
): RecyclerView.Adapter<LeaguesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(competitionXViewState: CompetitionXViewState) {
            val bind = ItemLeaguesBinding.bind(itemView)
            itemView.setOnClickListener {
                onItemClicked(competitionXViewState)
            }
            bind.apply {
                ivLeaguesImage.loadImage(competitionXViewState.emblem)
                tvLeaguesName.text = competitionXViewState.name
            }
        }
    }

    private val differCallback = object: DiffUtil.ItemCallback<CompetitionXViewState>() {
        override fun areItemsTheSame(oldItem: CompetitionXViewState, newItem: CompetitionXViewState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CompetitionXViewState, newItem: CompetitionXViewState): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_leagues, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}