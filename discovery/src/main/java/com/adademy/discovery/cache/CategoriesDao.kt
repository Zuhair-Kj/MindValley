package com.adademy.discovery.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adademy.discovery.model.Category
import com.adademy.discovery.model.Episode

const val CATEGORIES_TABLE_NAME = "categoriesTable"
@Dao
abstract class CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRows(entries: List<Category>)

    @Query("DELETE FROM $CATEGORIES_TABLE_NAME")
    abstract suspend fun clear()

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME")
    abstract suspend fun getAllRows(): List<Category>

    @Query("SELECT COUNT(*) FROM $CATEGORIES_TABLE_NAME")
    abstract suspend fun getRowsCount(): Int
}