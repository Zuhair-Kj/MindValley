package com.adademy.discovery.model

import com.adademy.discovery.ChannelNameAdapter
import com.adademy.discovery.CoverAssetAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class Episode(
    val title: String?,
    val type: String?,
    @JsonAdapter(CoverAssetAdapter::class) @SerializedName("coverAsset") val imageUrl: String?,
    @JsonAdapter(ChannelNameAdapter::class) @SerializedName("channel") val channelTitle: String?
)