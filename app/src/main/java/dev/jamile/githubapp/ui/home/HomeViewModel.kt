package dev.jamile.githubapp.ui.home

import dev.jamile.githubapp.models.SearchResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.jamile.githubapp.base.BaseViewModel
import dev.jamile.githubapp.base.ViewState
import dev.jamile.githubapp.models.ResponseStatus
import dev.jamile.githubapp.repository.ReposRepository
import dev.jamile.githubapp.utils.DispatcherProvider
import dev.jamile.githubapp.network.Result
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
            _reposLiveData.postValue(ViewState(status = ResponseStatus.LOADING))
            when (val response = repository.getRepositories()) {
                is Result.Success -> {
                    if (response.data.totalCount == 0) {
                        _reposLiveData.postValue(
                            ViewState(
                                null,
                                ResponseStatus.EMPTY_LIST
                            )
                        )
                    } else {
                        _reposLiveData.postValue(
                            ViewState(
                                response.data,
                                ResponseStatus.SUCCESS
                            )
                        )
                    }
                }
                is Result.Failure -> {
                    _reposLiveData.postValue(ViewState(null, ResponseStatus.ERROR, null))
                }
            }
        }
    }
}