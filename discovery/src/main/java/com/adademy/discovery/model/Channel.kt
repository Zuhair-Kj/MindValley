package com.adademy.discovery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.adademy.discovery.ThumbnailUrlAdapter
import com.adademy.discovery.cache.CHANNELS_TABLE_NAME
import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.koin.java.KoinJavaComponent
import java.lang.reflect.Type

@Entity(tableName = CHANNELS_TABLE_NAME)
data class Channel(
    @PrimaryKey @ColumnInfo(name = "title")val title: String = "",
    @ColumnInfo(name = "series") val series: List<Series>?,
    @ColumnInfo(name = "mediaCount") val mediaCount: Int = 0,
    @ColumnInfo(name = "latestMedia") val latestMedia: List<Episode>?,
    @ColumnInfo(name = "channelIconUrl")
    @JsonAdapter(ThumbnailUrlAdapter::class)
    @SerializedName("iconAsset")
    val channelIconUrl: String?
) {
    enum class ChannelType {
        SERIES, COURSE
    }

    fun getChannelType(): ChannelType {
        if (series?.isNullOrEmpty() == false) return ChannelType.SERIES
        return ChannelType.COURSE
    }
}

data class ChannelsList(
    val channels: List<Channel>?
)

data class ChannelsResponse(
    val data: ChannelsList?
)

class ChannelTypeConverter {
    @TypeConverter
    fun fromString(serialisedList: String): List<Channel> {
        val listType: Type = object : TypeToken<List<Channel>?>() {}.type
        return KoinJavaComponent.get(Gson::class.java).fromJson(serialisedList, listType)
    }

    @TypeConverter
    fun toString(list: List<Channel>): String {
        return KoinJavaComponent.get(Gson::class.java).toJson(list)
    }
}