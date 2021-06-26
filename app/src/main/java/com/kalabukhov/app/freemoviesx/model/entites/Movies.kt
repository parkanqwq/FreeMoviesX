package com.kalabukhov.app.freemoviesx.model.entites

import android.os.Parcelable
import com.kalabukhov.app.freemoviesx.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    val nameMovie: Movie = getDefaultCity1(),
    val id: Int? = 550,
    val original_title: String? = "name",
    val vote_average: Double? = 9.9,
    val release_date: String? = "1999.10.12",
    val original_language: String? = "US",
    val runtime: Int? = 140,
    val overview: String? = ""
) : Parcelable

fun getDefaultCity1() = Movie(550, "Форсаж", 9.9,
    "1999.10.12", "США", 140, "", R.drawable.spideman)

fun getRussianMovies() = listOf(
        Movies(Movie(290859, "Terminator: Dark Fate", 6.5,
            "1999.10.12", "США", 128, "", R.drawable.terminator)),
        Movies(Movie(557, "Spider-Man", 7.2,
            "550", "США", 121, "",R.drawable.spideman)),
        Movies(Movie(366924, "Batman: Bad Blood", 7.2,
            "550", "США", 72, "",R.drawable.betmen)),
    Movies(Movie(68721, "Iron Man 3", 6.9,
        "1999.10.12", "США", 130, "", R.drawable.suoermen))
    )