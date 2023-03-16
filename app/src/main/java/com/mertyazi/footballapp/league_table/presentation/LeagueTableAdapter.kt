package com.mertyazi.footballapp.league_table.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.databinding.ItemTableBinding
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader.loadImage

class LeagueTableAdapter(
    val onItemClicked: (TableViewState) -> Unit
): RecyclerView.Adapter<LeagueTableAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tableViewState: TableViewState) {
            val bind = ItemTableBinding.bind(itemView)
            itemView.setOnClickListener {
                onItemClicked(tableViewState)
            }
            bind.apply {
                tvPosition.text = tableViewState.position.toString()
                ivEmblem.loadImage(tableViewState.team.crest)
                tvName.text = tableViewState.team.name
                tvPlayed.text = tableViewState.playedGames.toString()
                tvWon.text = tableViewState.won.toString()
                tvDrawn.text = tableViewState.draw.toString()
                tvLost.text = tableViewState.lost.toString()
                tvPoints.text = tableViewState.points.toString()
            }
        }
    }

    private val differCallback = object: DiffUtil.ItemCallback<TableViewState>() {
        override fun areItemsTheSame(oldItem: TableViewState, newItem: TableViewState): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: TableViewState, newItem: TableViewState): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_table, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}