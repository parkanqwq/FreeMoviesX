package com.kalabukhov.app.freemoviesx.model.database

import android.database.Cursor
import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE original_title LIKE :original_title")
    fun getDataByNameMovie(original_title: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE original_title = :original_name")
    fun deleteByMovieName(original_name: String)

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id, original_title, original_language FROM HistoryEntity")
    fun getHistoryCursor(): Cursor

    @Query("SELECT id, original_title, original_language FROM HistoryEntity WHERE id = :id")
    fun getHistoryCursor(id: Long): Cursor
}