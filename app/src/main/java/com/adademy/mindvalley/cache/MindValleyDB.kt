package com.adademy.mindvalley.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adademy.discovery.cache.CategoriesDao
import com.adademy.discovery.cache.ChannelsDao
import com.adademy.discovery.cache.LatestEpisodesDao
import com.adademy.discovery.model.*

const val DATABASE_NAME = "mind-valley-db"
@Database(entities = [Category::class, Episode::class, Channel::class, Series::class], version = 1)
@TypeConverters(CategoryTypeConverter::class, EpisodeTypeConverter::class, ChannelTypeConverter::class, SeriesTypeConverter::class)
abstract class MindValleyDB: RoomDatabase() {
    abstract fun channelsDao(): ChannelsDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun latestEpisodesDao(): LatestEpisodesDao
}