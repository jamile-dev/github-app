package dev.jamile.githubapp.repository

import dev.jamile.githubapp.models.SearchResponse
import dev.jamile.githubapp.network.Result

interface ReposRepository  {
    suspend fun getRepositories(): Result<SearchResponse>
    suspend fun searchRepositories(query: String): Result<SearchResponse>
}
