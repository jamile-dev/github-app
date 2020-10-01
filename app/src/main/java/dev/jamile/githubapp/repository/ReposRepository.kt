package dev.jamile.githubapp.repository

import SearchResponse
import dev.jamile.githubapp.network.Result


interface ReposRepository  {
    suspend fun getRepositories(): Result<SearchResponse>
    suspend fun searchRepositories(): Result<SearchResponse>
}