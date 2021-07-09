package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.entites.Movie
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.entites.NoteMovie

interface Repository {
    fun getMoviesFromServer(id: Int): Movies
    fun getMovieFromLocalStorage(): List<Movies>
    fun getAllHistory(): List<Movies>
    fun getAllNote(): List<Movies>
    fun saveEntity(movies: Movies)
    fun saveNote(movies: Movies)
}