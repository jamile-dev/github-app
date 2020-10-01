package dev.jamile.githubapp.network

import okhttp3.OkHttpClient

object RequestInterceptor {
    fun setupOkHttp(): OkHttpClient.Builder {
        val okHttp = OkHttpClient.Builder()
        okHttp.addInterceptor { chain ->
            return@addInterceptor chain.proceed(chain.request())
        }
        return okHttp
    }
}