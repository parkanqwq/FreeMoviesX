package com.kalabukhov.app.freemoviesx.model.database_note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity (
    @PrimaryKey(autoGenerate = true) val idData: Long,
    val id: Int,
    val original_title: String,
    val vote_average: Double,
    val release_date: String,
    val original_language: String,
    val runtime: Int,
    val overview: String,
    val backdrop_path: String,
    val adult: Boolean,
    val note: String
)