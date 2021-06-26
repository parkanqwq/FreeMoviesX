package com.kalabukhov.app.freemoviesx.framework.ui

import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.repository.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(id: Int) {
        liveDataToObserver.value = AppState.Loading
        Thread {
            val data = repository.getMoviesFromServer(id)
            liveDataToObserver.postValue(AppState.Success(listOf(data)))
        }.start()
    }
}