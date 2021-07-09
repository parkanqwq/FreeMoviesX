package com.kalabukhov.app.freemoviesx.di

import com.kalabukhov.app.freemoviesx.framework.ui.details_fragment.DetailsViewModel
import com.kalabukhov.app.freemoviesx.framework.ui.history_fragment.HistoryViewModel
import com.kalabukhov.app.freemoviesx.framework.ui.main_fragment.MainViewModel
import com.kalabukhov.app.freemoviesx.framework.ui.note_fragment.NoteViewModel
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import com.kalabukhov.app.freemoviesx.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { NoteViewModel(get()) }
}