package dev.jamile.githubapp

import android.app.Application
import android.content.Context
import dev.jamile.githubapp.di.networkModule
import dev.jamile.githubapp.di.repositoryModule
import dev.jamile.githubapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupApp(baseContext)
    }

    private fun setupApp(context: Context) {
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                )
            )
        }
    }

}