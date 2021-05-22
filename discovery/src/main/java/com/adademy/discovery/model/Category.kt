package com.adademy.discovery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.adademy.discovery.cache.CATEGORIES_TABLE_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.java.KoinJavaComponent
import java.lang.reflect.Type

@Entity(tableName = CATEGORIES_TABLE_NAME)
data class Category(
    @PrimaryKey @ColumnInfo(name = "name") val name: String = ""
)

data class CategoryList(
    val categories: List<Category>?
)

data class CategoriesResponse(
    val data: CategoryList?
)

class CategoryTypeConverter {
    @TypeConverter
    fun fromString(serialisedList: String): List<Category> {
        val listType: Type = object : TypeToken<List<Category>?>() {}.type
        return KoinJavaComponent.get(Gson::class.java).fromJson(serialisedList, listType)
    }

    @TypeConverter
    fun toString(list: List<Category>): String {
        return KoinJavaComponent.get(Gson::class.java).toJson(list)
    }
}