package dev.jamile.githubapp.network

import dev.jamile.githubapp.models.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories?sort=stars")
    suspend fun fetchPopularRepos(
        @Query("q") query: String = "stars",
    ): Response<SearchResponse>

    @GET("/search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): Response<SearchResponse>

}