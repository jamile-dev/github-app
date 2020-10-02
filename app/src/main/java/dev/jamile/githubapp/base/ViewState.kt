package dev.jamile.githubapp.base

class ViewState<T,S>(
    val data: T? = null,
    val status: S,
    val error: Throwable? = null
)