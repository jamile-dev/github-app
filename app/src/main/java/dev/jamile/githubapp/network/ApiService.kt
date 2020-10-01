package dev.jamile.githubapp.network

import SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories?sort=stars")
    suspend fun fetchRepos(
        @Query("q") query: String = "language:kotlin",
    ): Response<SearchResponse>

    @GET("/search/repositories")
    suspend fun searchRepositories(): Response<List<SearchResponse>>

}