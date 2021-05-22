package com.adademy.discovery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.adademy.discovery.ChannelNameAdapter
import com.adademy.discovery.CoverAssetAdapter
import com.adademy.discovery.cache.LATEST_EPISODES_TABLE_NAME
import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.koin.java.KoinJavaComponent
import java.lang.reflect.Type

@Entity(tableName = LATEST_EPISODES_TABLE_NAME)
data class Episode(
    @PrimaryKey
    @ColumnInfo(name ="title")
    val title: String = "",
    @ColumnInfo(name = "type")
    val type: String?,
    @JsonAdapter(CoverAssetAdapter::class)
    @SerializedName("coverAsset")
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @JsonAdapter(ChannelNameAdapter::class)
    @SerializedName("channel")
    @ColumnInfo(name = "channelTitle")
    val channelTitle: String?
)

class EpisodeTypeConverter {
    @TypeConverter
    fun fromString(serialisedList: String): List<Episode> {
        val listType: Type = object : TypeToken<List<Episode>?>() {}.type
        return KoinJavaComponent.get(Gson::class.java).fromJson(serialisedList, listType)
    }

    @TypeConverter
    fun toString(list: List<Episode>): String {
        return KoinJavaComponent.get(Gson::class.java).toJson(list)
    }
}