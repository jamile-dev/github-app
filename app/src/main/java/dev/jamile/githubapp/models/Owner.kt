package com.example.githubapp.models

import com.google.gson.annotations.SerializedName

data class Owner(
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val login: String,
)