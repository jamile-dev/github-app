package dev.jamile.githubapp.repository

import dev.jamile.githubapp.models.SearchResponse
import dev.jamile.githubapp.network.ApiService
import dev.jamile.githubapp.network.Result


class ReposRepositoryImpl(private val apiService: ApiService) : ReposRepository {
    override suspend fun getRepositories(): Result<SearchResponse> {
        val response = apiService.fetchPopularRepos()
        if (response.isSuccessful && response.body()?.incompleteResults == false) {
            return Result.Success(response.body()!!)
        }
        return Result.Failure(Throwable("Error ${response.errorBody()} ${response.message()} "))
    }

    override suspend fun searchRepositories(query: String): Result<SearchResponse> {
        val response = apiService.searchRepositories(query)
        if (response.isSuccessful) {
            return Result.Success(response.body()!!)
        }
        return Result.Failure(Throwable("Error ${response.errorBody()} ${response.message()} "))
    }
}