package com.adademy.discovery.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adademy.discovery.model.Channel

const val CHANNELS_TABLE_NAME = "channelsTable"
@Dao
abstract class ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRows(newEntries: List<Channel>)

    @Query("DELETE FROM $CHANNELS_TABLE_NAME")
    abstract suspend fun clear()

    @Query("SELECT * FROM $CHANNELS_TABLE_NAME")
    abstract suspend fun getAllRows(): List<Channel>

    @Query("SELECT COUNT(*) FROM $CHANNELS_TABLE_NAME")
    abstract suspend fun getRowsCount(): Int
}