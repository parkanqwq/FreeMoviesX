package com.kalabukhov.app.freemoviesx.framework.ui.main_fragment

import androidx.lifecycle.*
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getMoviesFilm() = getDataFromMovieSource()

    private fun getDataFromMovieSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(oneSecond)
            liveDataToObserve.postValue(
                AppState.Success(repository.getMovieFromLocalStorage()))
        }.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart() {
        // тут запустится процев в онСтарт
    }

    companion object {
        const val oneSecond: Long = 1000
    }
}