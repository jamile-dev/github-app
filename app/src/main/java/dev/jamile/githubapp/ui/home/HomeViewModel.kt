package dev.jamile.githubapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jamile.githubapp.base.BaseViewModel
import dev.jamile.githubapp.base.ViewState
import dev.jamile.githubapp.models.ResponseStatus
import dev.jamile.githubapp.models.ResponseStatus.*
import dev.jamile.githubapp.models.SearchResponse
import dev.jamile.githubapp.network.Result
import dev.jamile.githubapp.repository.ReposRepository
import dev.jamile.githubapp.utils.DispatcherProvider
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: ReposRepository
) : BaseViewModel(dispatcherProvider) {

    private val _reposLiveData =
        MutableLiveData<ViewState<SearchResponse, ResponseStatus>>()
    val reposLiveData: LiveData<ViewState<SearchResponse, ResponseStatus>> =
        _reposLiveData

    fun getRepositoriesList() {
        scope.launch(dispatcherProvider.ui) {
            _reposLiveData.postValue(ViewState(status = LOADING))
            when (val response = repository.getRepositories()) {
                is Result.Success -> if (response.data.totalCount == 0) {
                    _reposLiveData.postValue(
                        ViewState(
                            null,
                            EMPTY_LIST
                        )
                    )
                } else {
                    _reposLiveData.postValue(
                        ViewState(
                            response.data,
                            SUCCESS
                        )
                    )
                }
                is Result.Failure -> _reposLiveData.postValue(
                    ViewState(
                        null,
                        ERROR,
                        response.throwable
                    )
                )
            }
        }
    }
}
