package dev.jamile.githubapp.testUtils

import dev.jamile.githubapp.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : DispatcherProvider() {
    override val ui: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
}