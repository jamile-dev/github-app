package dev.jamile.githubapp.ui.search

import android.util.Log
import dev.jamile.githubapp.models.SearchResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.jamile.githubapp.base.BaseViewModel
import dev.jamile.githubapp.base.ViewState
import dev.jamile.githubapp.models.ResponseStatus
import dev.jamile.githubapp.repository.ReposRepository
import dev.jamile.githubapp.utils.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import dev.jamile.githubapp.network.Result
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SearchViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: ReposRepository
) : BaseViewModel(dispatcherProvider) {

    private val _searchLiveData = MutableLiveData<ViewState<SearchResponse, ResponseStatus>>()
    val searchLiveData: LiveData<ViewState<SearchResponse, ResponseStatus>> =
        _searchLiveData

    @FlowPreview
    fun searchQuery(text: Flow<String>) {
        viewModelScope.launch(dispatcherProvider.io) {
            text.debounce(DEBOUNCE_VALUE)
                .distinctUntilChanged()
                .filterNotNull()
                .filterNot { it.isBlank() }
                .collect { text ->
                    searchInstruments(text)
                }
        }
    }

    private suspend fun searchInstruments(repoName: String) {
        _searchLiveData.postValue(ViewState(status = ResponseStatus.LOADING))
        when (val response = repository.searchRepositories(repoName)) {
            is Result.Success -> {
                _searchLiveData.postValue(
                    ViewState(
                        response.data,
                        ResponseStatus.SUCCESS
                    )
                )
            }
            is Result.Failure -> {
                _searchLiveData.postValue(ViewState(null, ResponseStatus.ERROR, null))
            }
        }
    }

    companion object {
        const val DEBOUNCE_VALUE = 500L
    }

}