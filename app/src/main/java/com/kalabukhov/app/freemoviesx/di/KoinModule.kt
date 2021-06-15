package com.kalabukhov.app.freemoviesx.di

import com.kalabukhov.app.freemoviesx.framework.ui.MainViewModel
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import com.kalabukhov.app.freemoviesx.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
}