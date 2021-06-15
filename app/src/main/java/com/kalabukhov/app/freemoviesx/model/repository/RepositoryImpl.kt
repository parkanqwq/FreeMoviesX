package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.entites.Movies

class RepositoryImpl : Repository {
    override fun getMoviesFromServer(): Movies {
        return Movies()
    }

    override fun getMovieFromLocalStorage(): Movies {
        return Movies()
    }
}