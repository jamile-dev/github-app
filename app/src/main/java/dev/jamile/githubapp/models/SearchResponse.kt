package dev.jamile.githubapp.models
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Repository>,
    @SerializedName("total_count")
    val totalCount: Int
)