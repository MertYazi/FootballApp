package com.mertyazi.footballapp.view.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mertyazi.footballapp.databinding.ItemFilterMatchBinding
import com.mertyazi.footballapp.model.CompetitionX
import com.mertyazi.footballapp.utils.Constants.loadUrl
import com.mertyazi.footballapp.view.fragments.MatchesTodayFragment

class FiltersAdapter(val fragment: MatchesTodayFragment): RecyclerView.Adapter<FiltersAdapter.ViewHolder>() {

    private var leagues: MutableList<CompetitionX> = mutableListOf()

    class ViewHolder(view: ItemFilterMatchBinding): RecyclerView.ViewHolder(view.root) {
        val leagueName = view.tvFilterMatch
        val leagueEmblem = view.ivFilterMatch
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFilterMatchBinding = ItemFilterMatchBinding
            .inflate(LayoutInflater.from(fragment.activity), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = leagues[position]
        holder.leagueName.text = filter.name
        holder.leagueEmblem.loadUrl(filter.emblem)
        holder.itemView.setOnClickListener {
            fragment.filterSelection(filter)
        }
    }

    override fun getItemCount(): Int {
        return leagues.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun leaguesList(list: MutableList<CompetitionX>) {
        leagues = list
        notifyDataSetChanged()
    }
}