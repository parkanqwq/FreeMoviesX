package com.kalabukhov.app.freemoviesx.model.entites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteMovie (
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
) : Parcelable