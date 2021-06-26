package com.kalabukhov.app.freemoviesx.model.entites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val name: String,
    val starsMovie: Double,
    val release_date: String,
    val original_language: String,
    val runtime: Int,
    val overview: String,
    val image: Int
) : Parcelable