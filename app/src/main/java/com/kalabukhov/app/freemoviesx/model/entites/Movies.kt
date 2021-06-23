package com.kalabukhov.app.freemoviesx.model.entites

import android.os.Parcelable
import com.kalabukhov.app.freemoviesx.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    val nameMovie: Movie = getDefaultCity1()
) : Parcelable

fun getDefaultCity1() = Movie("Super Men", 8.6, "5", R.drawable.forsazh,
    "1:15", 2015, "США")

fun getRussianMovies() = listOf(
        Movies(Movie("Супер мен", 7.7, "3", R.drawable.suoermen,
            "1:46", 2015, "США")),
        Movies(Movie("Человек паук", 6.8, "359", R.drawable.spideman,
            "2:13", 2012, "США")),
        Movies(Movie("Бэтмен", 8.6, "329", R.drawable.betmen,
            "2:11", 2019, "США")),
        Movies(Movie("Терминатор", 9.2, "5", R.drawable.terminator,
            "2:18", 2013, "США")),
        Movies(Movie("Форсаж", 8.6, "3559", R.drawable.forsazh,
            "1:55", 2018, "США"))
    )