package com.kalabukhov.app.freemoviesx.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val idData: Long = 0,
    val id: Int = 0,
    val original_title: String = "",
    val vote_average: Double = 2.2,
    val release_date: String = "",
    val original_language: String = "",
    val runtime: Int = 0,
    val overview: String = "",
    val backdrop_path: String = "",
    val adult: Boolean = false
)

const val ID = "id"
const val ORIGINAL_TITLE = "original_title"
const val RELEASE_DATE = "release_date"