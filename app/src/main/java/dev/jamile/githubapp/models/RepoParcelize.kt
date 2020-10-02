package dev.jamile.githubapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoParcelize(
    val repoImage: String,
    val repoOwnerName: String,
    val repoName: String,
    val repoDescription: String,
    val repoUrl: String,
    val repoStars: String,
    val repoForks: String,
    val repoIssues: String,
    val repoWatchers: String,
    val repoLanguage: String,
) : Parcelable