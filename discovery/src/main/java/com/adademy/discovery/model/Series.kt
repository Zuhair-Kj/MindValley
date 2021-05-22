package com.adademy.discovery.model

import com.adademy.discovery.CoverAssetAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class Series(
    val title: String?,
    @JsonAdapter(CoverAssetAdapter::class) @SerializedName("coverAsset")val imageUrl: String?
)