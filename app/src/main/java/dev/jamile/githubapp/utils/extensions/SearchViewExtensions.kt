package dev.jamile.githubapp.utils.extensions

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun SearchView.textChangeAsFlow(): Flow<String> {
    return callbackFlow {
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                offerSafely(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                offerSafely(query)
                return true
            }
        }
        setOnQueryTextListener(listener)
        awaitClose {
            setOnQueryTextListener(null)
        }
    }
}