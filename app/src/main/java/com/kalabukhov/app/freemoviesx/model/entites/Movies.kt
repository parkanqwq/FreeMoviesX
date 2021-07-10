package com.kalabukhov.app.freemoviesx.model.entites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    var nameMovie: Movie = getDefaultCity1(),
    var noteMovie: NoteMovie = getDefaultCity3(),
    val MovieArr: MovieForArr = getDefaultCity2(),
    val id: Int? = 550,
    val name: String? = "name",
    val vote_average: Double? = 9.9,
    val release_date: String? = "1999.10.12",
    val place_of_birth: String? = "US",
    val runtime: Int? = 140,
    val overview: String? = "",
    val poster_path: String? = "",
    val adult: Boolean? = false,
    val note: String? = "note",
) : Parcelable

fun getDefaultCity1() = Movie(580, "Форсаж", 9.9,
    "1999.10.12", "США", 140, "",
    "", false, "")

fun getDefaultCity2() = MovieForArr(508, "Форсаж12",4.8,"", "")

fun getDefaultCity3() = NoteMovie(520, "Sirius Sem", 9.9,
    "1999.10.12", "США", 140, "",
    "", false, "n")
