package dev.jamile.githubapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jamile.githubapp.di.networkModule
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
                listOf(networkModule)
            )
        }
    }

}