package com.adademy.discovery.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adademy.discovery.model.Episode

const val LATEST_EPISODES_TABLE_NAME = "latestEpisodesTable"
@Dao
abstract class LatestEpisodesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRows(entries: List<Episode>)

    @Query("DELETE FROM $LATEST_EPISODES_TABLE_NAME")
    abstract suspend fun clear()

    @Query("SELECT * FROM $LATEST_EPISODES_TABLE_NAME")
    abstract suspend fun getAllRows(): List<Episode>

    @Query("SELECT COUNT(*) FROM $LATEST_EPISODES_TABLE_NAME")
    abstract suspend fun getRowsCount(): Int
}