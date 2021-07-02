package com.kalabukhov.app.freemoviesx.framework.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import kotlinx.coroutines.*

class DetailsViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {
    val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(id: Int) {
        liveDataToObserver.value = AppState.Loading
        launch {
            val job = async(Dispatchers.IO) { repository.getMoviesFromServer(id) }
            liveDataToObserver.value = AppState.Success(listOf(job.await()))
        }
    }
}