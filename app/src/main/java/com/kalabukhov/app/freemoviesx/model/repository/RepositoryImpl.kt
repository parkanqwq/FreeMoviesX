package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.database.Database
import com.kalabukhov.app.freemoviesx.model.database.HistoryEntity
import com.kalabukhov.app.freemoviesx.model.database_note.NoteEntity
import com.kalabukhov.app.freemoviesx.model.entites.Movie
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.rest.MoviesRepo

var QUERY = "Человек паук"
var ADULT = false
var PAGE = 1

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(id: Int): Movies {
        //val dto = MoviesLoader.loadWeather(id)
        val dto = MoviesRepo.api.getMoviesAsync(
            id, "a7fe7b51456e94640008bbb9e3a50dd5",
            "ru"
        ).execute().body()
        return Movies(
            id = dto?.id,
            original_title = dto?.original_title,
            vote_average = dto?.vote_average,
            release_date = dto?.release_date,
            original_language = dto?.original_language,
            runtime = dto?.runtime,
            overview = dto?.overview,
            backdrop_path = dto?.backdrop_path,
            adult = dto?.adult
        )
    }

    override fun getMovieFromLocalStorage(): ArrayList<Movies> {
        val dto = MoviesRepo.api.getNextPage(
            "a7fe7b51456e94640008bbb9e3a50dd5",
            PAGE, ADULT, QUERY
        ).execute().body()

        val filmsRes = ArrayList<Movies>()
        filmsRes.clear()
        if (dto?.results != null) {
            for (moviesDTO in dto.results) {
                if (moviesDTO.backdrop_path != null)
                 {
                    filmsRes.add(
                        Movies(
                            Movie(
                                moviesDTO.id!!,
                                moviesDTO.original_title!!, moviesDTO.vote_average!!,
                                moviesDTO.release_date!!, moviesDTO.original_language!!,
                                0, "", moviesDTO.backdrop_path,
                                false, ""
                            )
                        )
                    )
                }
            }
        }
        return filmsRes
    }

    override fun getAllHistory(): List<Movies> =
        convertHistoryEntityToMovies(Database.db.historyDao().all())

    override fun getAllNote(): List<Movies> =
        convertHistoryEntityToMoviesNote(com.kalabukhov.app.freemoviesx.model.database_note.Database.db.noteDao().all())

    override fun saveEntity(movies: Movies) {
        Database.db.historyDao().insert(convertMoviesToEntity(movies))
    }

    override fun saveNote(movies: Movies) {
        com.kalabukhov.app.freemoviesx.model.database_note.Database.db.noteDao().insert(convertMoviesToEntityNote(movies))
    }

    private fun convertHistoryEntityToMovies(entityList: List<HistoryEntity>): List<Movies> =
        entityList.map {
            Movies(
                Movie(
                    it.id, it.original_title, it.vote_average, it.release_date,
                    it.original_language, it.runtime,
                    it.overview, it.backdrop_path, it.adult, ""
                )
            )
        }.asReversed()



    private fun convertHistoryEntityToMoviesNote(entityList: List<NoteEntity>): List<Movies> =
        entityList.map {
            Movies(
                Movie(
                    it.id, it.original_title, it.vote_average, it.release_date,
                    it.original_language, it.runtime,
                    it.overview, it.backdrop_path, it.adult, it.note
                )
            )
        }.asReversed()

    private fun convertMoviesToEntity(movies: Movies): HistoryEntity =
        HistoryEntity(
            0, movies.nameMovie.id, movies.nameMovie.original_title,
            movies.nameMovie.vote_average, movies.nameMovie.release_date,
            movies.nameMovie.original_language, movies.nameMovie.runtime,
            movies.nameMovie.overview, movies.nameMovie.backdrop_path,
            movies.nameMovie.adult
        )

    private fun convertMoviesToEntityNote(movies: Movies): NoteEntity =
        NoteEntity(
            0, movies.nameMovie.id, movies.nameMovie.original_title,
            movies.nameMovie.vote_average, movies.nameMovie.release_date,
            movies.nameMovie.original_language, movies.nameMovie.runtime,
            movies.nameMovie.overview, movies.nameMovie.backdrop_path,
            movies.nameMovie.adult, movies.nameMovie.note
        )
}

