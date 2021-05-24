package com.adademy.discovery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adademy.discovery.databinding.ItemRowChannelBinding
import com.adademy.discovery.databinding.ItemRowLatestEpisodesBinding
import com.adademy.discovery.model.Channel
import com.adademy.discovery.model.LatestEpisodesList
import com.adademy.discovery.ui.viewholders.RowChannelViewHolder
import com.adademy.discovery.ui.viewholders.RowLatestEpisodesViewHolder

const val VIEW_TYPE_ROW_LATEST_EPISODES = 1001
const val VIEW_TYPE_ROW_CHANNELS = 1002
//const val VIEW_TYPE_ROW_SERIES = 1003
const val VIEW_TYPE_ROW_CATEGORIES = 1004
class DiscoveryAdapter(private val items: MutableList<Any>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun addItems(newItems: List<Any>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is LatestEpisodesList -> VIEW_TYPE_ROW_LATEST_EPISODES
            is Channel -> VIEW_TYPE_ROW_CHANNELS
            else -> VIEW_TYPE_ROW_LATEST_EPISODES
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ROW_LATEST_EPISODES -> {
               RowLatestEpisodesViewHolder(ItemRowLatestEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            VIEW_TYPE_ROW_CHANNELS -> {
                RowChannelViewHolder(ItemRowChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                RowLatestEpisodesViewHolder(ItemRowLatestEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RowLatestEpisodesViewHolder -> {
                (items[position] as LatestEpisodesList?)?.let { latestEpisodesList ->
                    latestEpisodesList.media?.let {
                        holder.bind(it)
                    }
                }

            }

            is RowChannelViewHolder -> {
                holder.bind(items[position] as Channel)
            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}