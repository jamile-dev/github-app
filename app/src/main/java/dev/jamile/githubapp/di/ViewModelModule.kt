package dev.jamile.githubapp.di

import dev.jamile.githubapp.ui.home.HomeViewModel
import dev.jamile.githubapp.ui.search.SearchViewModel
import dev.jamile.githubapp.utils.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {
    factory { DispatcherProvider() }
    factory { HomeViewModel(get(), get()) }
    factory { SearchViewModel(get(), get()) }
}