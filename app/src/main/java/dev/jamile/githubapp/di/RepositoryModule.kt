package dev.jamile.githubapp.di

import dev.jamile.githubapp.repository.ReposRepository
import dev.jamile.githubapp.repository.ReposRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<ReposRepository> { ReposRepositoryImpl(get()) }
}