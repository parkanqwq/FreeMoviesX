package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.MoviesLoader
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.entites.getRussianMovies

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(id: Int): Movies {
        val dto = MoviesLoader.loadWeather(id)
        return Movies(
            id = dto?.id,
            original_title = dto?.original_title,
            vote_average = dto?.vote_average,
            release_date = dto?.release_date,
            original_language = dto?.original_language,
            runtime = dto?.runtime,
            overview = dto?.overview
        )
    }

    override fun getMovieFromLocalStorage() = getRussianMovies()
}