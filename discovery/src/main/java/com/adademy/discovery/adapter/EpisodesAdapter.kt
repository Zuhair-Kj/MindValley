package com.adademy.discovery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.databinding.ItemEpisodeBinding
import com.adademy.discovery.databinding.ItemSeriesBinding
import com.adademy.discovery.model.Episode
import com.adademy.discovery.ui.viewholders.EpisodeViewHolder
import com.adademy.discovery.ui.viewholders.SeriesViewHolder

const val VIEW_TYPE_EPISODE = 1
const val VIEW_TYPE_SERIES = 2
class EpisodesAdapter(
    private val episodes: MutableList<Episode>,
    private var viewType: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun replace(episodes: List<Episode>, viewType: Int) {
        this.episodes.clear()
        this.episodes.addAll(episodes.take(6))
        this.viewType = viewType
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int): Int {
        return viewType
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_EPISODE) {
            return EpisodeViewHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return SeriesViewHolder(ItemSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is EpisodeViewHolder -> holder.bind(episodes[position])
            is SeriesViewHolder -> holder.bind(episodes[position])
        }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

}