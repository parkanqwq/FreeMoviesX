package com.kalabukhov.app.freemoviesx.framework.ui.main_fragment

import androidx.lifecycle.*
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.repository.ADULT
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import kotlinx.coroutines.*

class MainViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getMoviesFilm() = getDataFromMovieSource()

    private fun getDataFromMovieSource() {
        liveDataToObserve.value = AppState.Loading
        launch {
            val localStorageJob = async(Dispatchers.IO) {
                repository.getMovieFromLocalStorage()
            }
            liveDataToObserve.value = AppState.Success(localStorageJob.await())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart() {
        // тут запустится процев в онСтарт
    }
}