package com.adademy.discovery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.adademy.discovery.CoverAssetAdapter
import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.koin.java.KoinJavaComponent
import java.lang.reflect.Type

@Entity
data class Series(
    @PrimaryKey @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "imageUrl") @JsonAdapter(CoverAssetAdapter::class) @SerializedName("coverAsset")val imageUrl: String?
)

class SeriesTypeConverter {
    @TypeConverter
    fun fromString(serialisedList: String): List<Series> {
        val listType: Type = object : TypeToken<List<Series>?>() {}.type
        return KoinJavaComponent.get(Gson::class.java).fromJson(serialisedList, listType)
    }

    @TypeConverter
    fun toString(list: List<Series>): String {
        return KoinJavaComponent.get(Gson::class.java).toJson(list)
    }
}