package com.mertyazi.footballapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.databinding.ItemTableBinding
import com.mertyazi.footballapp.model.Table
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.fragments.LeagueTableFragment
import com.mertyazi.footballapp.view.fragments.LeagueTableFragmentDirections

class TablesAdapter(private val fragment: LeagueTableFragment): RecyclerView.Adapter<TablesAdapter.ViewHolder>() {

    class ViewHolder(view: ItemTableBinding): RecyclerView.ViewHolder(view.root) {
        val tvPosition = view.tvPosition
        val ivImage = view.ivEmblem
        val tvName = view.tvName
        val tvPlayed = view.tvPlayed
        val tvWon = view.tvWon
        val tvDrawn = view.tvDrawn
        val tvLost = view.tvLost
        val tvPoints = view.tvPoints
    }

    private val differCallback = object: DiffUtil.ItemCallback<Table>() {
        override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTableBinding =
            ItemTableBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = differ.currentList[position]
        holder.apply {
            tvPosition.text = team.position.toString()
            ivImage.loadUrl(team.team.crest)
            tvName.text = team.team.name
            tvPlayed.text = team.playedGames.toString()
            tvWon.text = team.won.toString()
            tvDrawn.text = team.draw.toString()
            tvLost.text = team.lost.toString()
            tvPoints.text = team.points.toString()

            itemView.setOnClickListener {
                fragment.findNavController().navigate(
                    LeagueTableFragmentDirections.actionLeagueTableFragmentToTeamDetailsFragment(
                        team.team.id,
                        team.team.name,
                        team.team.crest
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}