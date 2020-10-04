package dev.jamile.githubapp.models

import com.google.gson.annotations.SerializedName



data class Repository(
    val id: Int,
    val name: String,
    val owner: Owner,
    val description: String,
    @SerializedName("forks_count")
    val forks: String,
    @SerializedName("open_issues_count")
    val issues: Int,
    @SerializedName("stargazers_count")
    val startGazersCount: Int,
    val watchers: Int,
    val language: String,
    @SerializedName("html_url")
    val url: String,
)
