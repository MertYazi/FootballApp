package com.mertyazi.footballapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.databinding.ItemLeaguesBinding
import com.mertyazi.footballapp.model.CompetitionX
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.fragments.LeaguesFragment
import com.mertyazi.footballapp.view.fragments.LeaguesFragmentDirections

class LeaguesAdapter(private val fragment: LeaguesFragment): RecyclerView.Adapter<LeaguesAdapter.ViewHolder>() {

    class ViewHolder(view: ItemLeaguesBinding): RecyclerView.ViewHolder(view.root) {
        val ivLeagueImage = view.ivLeaguesImage
        val tvLeagueName = view.tvLeaguesName
    }

    private val differCallback = object: DiffUtil.ItemCallback<CompetitionX>() {
        override fun areItemsTheSame(oldItem: CompetitionX, newItem: CompetitionX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CompetitionX, newItem: CompetitionX): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLeaguesBinding =
            ItemLeaguesBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = differ.currentList[position]
        holder.apply {
            ivLeagueImage.loadUrl(league.emblem)
            tvLeagueName.text = league.name

            itemView.setOnClickListener {
                fragment.findNavController().navigate(
                    LeaguesFragmentDirections.actionLeaguesFragmentToLeagueTableFragment(
                        league.code,
                        league.name,
                        league.emblem
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}