package com.kalabukhov.app.freemoviesx.framework.ui.details_fragment

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.entites.Movie
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import kotlinx.coroutines.*

class DetailsViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {
    val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(movies: Movies) {
        liveDataToObserver.value = AppState.Loading
        launch {
            val job = async(Dispatchers.IO) {
                val data = repository.getMoviesFromServer(movies.nameMovie.id)
                data.nameMovie = movies.nameMovie
                repository.saveEntity(data)
                data
            }
            liveDataToObserver.value = AppState.Success(arrayListOf(job.await()))
        }
    }
    fun createNote(movies: Movies, note: String) {
        launch {
            val job = async(Dispatchers.IO) {
                val data = repository.getMoviesFromServer(movies.nameMovie.id)
                data.nameMovie = movies.nameMovie
                data.nameMovie.note = note
                repository.saveNote(data)
                data
            }
            liveDataToObserver.value = AppState.Success(arrayListOf(job.await()))
        }
    }
}