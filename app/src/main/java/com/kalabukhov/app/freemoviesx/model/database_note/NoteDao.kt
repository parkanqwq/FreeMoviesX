package com.kalabukhov.app.freemoviesx.model.database_note

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM NoteEntity")
    fun all(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE original_title LIKE :original_title")
    fun getDataByNameMovie(original_title: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NoteEntity)

    @Update
    fun update(entity: NoteEntity)

    @Delete
    fun delete(entity: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE original_title = :original_name")
    fun deleteByMovieName(original_name: String)
}