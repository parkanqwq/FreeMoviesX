package com.kalabukhov.app.freemoviesx.model

import com.kalabukhov.app.freemoviesx.model.entites.Movies

sealed class AppState {
    data class Success(val moviesData: Movies) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}