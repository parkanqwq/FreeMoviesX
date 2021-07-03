package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.MoviesLoader
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.entites.getRussianMovies
import com.kalabukhov.app.freemoviesx.model.rest.MoviesRepo

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(id: Int): Movies {
         //val dto = MoviesLoader.loadWeather(id)
        val dto = MoviesRepo.api.getMoviesAsync(id,"a7fe7b51456e94640008bbb9e3a50dd5", "ru").execute().body()
        return Movies(
            id = dto?.id,
            original_title = dto?.original_title,
            vote_average = dto?.vote_average,
            release_date = dto?.release_date,
            original_language = dto?.original_language,
            runtime = dto?.runtime,
            overview = dto?.overview,
            backdrop_path = dto?.backdrop_path
        )
    }

    override fun getMovieFromLocalStorage() = getRussianMovies()
}