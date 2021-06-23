package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.entites.getRussianMovies

class RepositoryImpl : Repository {
    override fun getMoviesFromServer() = Movies()

    override fun getMovieFromLocalStorage() = getRussianMovies()
}