package com.kalabukhov.app.freemoviesx.model.entites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val nameMovie: String,
    val starsMovie: Double,
    val id: String,
    val image: Int,
    val timeMovie: String,
    val age: Int,
    val country: String
) : Parcelable