package com.kalabukhov.app.freemoviesx.framework.ui.note_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NoteViewModel  (
    private val repository: Repository
) : ViewModel(), CoroutineScope by MainScope() {
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getAllNote() {
        historyLiveData.value = AppState.Loading
        launch(Dispatchers.IO) {
            historyLiveData.postValue(AppState.Success(repository.getAllNote()))
        }
    }
}