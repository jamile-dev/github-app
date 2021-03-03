package dev.jamile.githubapp.utils.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.channels.ProducerScope

@ExperimentalCoroutinesApi
fun <E> ProducerScope<E>.offerSafely(element: E): Boolean {
    return try {
        if (!isClosedForSend) {
            offer(element)
        } else false
    } catch (ex: ClosedSendChannelException) {
        false
    }
}
