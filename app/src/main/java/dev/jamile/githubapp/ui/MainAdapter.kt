package dev.jamile.githubapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.RepoParcelize
import dev.jamile.githubapp.models.Repository
import dev.jamile.githubapp.ui.home.HomeFragmentDirections
import dev.jamile.githubapp.ui.search.SearchFragmentDirections
import dev.jamile.githubapp.utils.Directions
import dev.jamile.githubapp.utils.Directions.DETAIL
import dev.jamile.githubapp.utils.Directions.SEARCH
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import kotlinx.android.synthetic.main.repo_item_layout.view.*
import kotlinx.android.synthetic.main.search_repo_list_item.view.*

class MainAdapter(
    private val context: Context,
    private val reposList: List<Repository>,
    private val layout: Int,
    private val navDirections: Directions
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(layout, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = reposList[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = reposList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repository) {
            itemView.apply {
                if (navDirections === SEARCH) {
                    Glide
                        .with(itemView)
                        .load(repo.owner.avatarUrl)
                        .placeholder(R.drawable.octocat)
                        .into(authorImage)
                    ownerName.text = repo.owner.login
                    repositoryName.text = repo.name
                    repoDescription.text = repo.description
                    stars.text = repo.startGazersCount.toString()
                    langName.text = repo.language
                    setupContainerClick(container, repo)
                } else {
                    Glide
                        .with(itemView)
                        .load(repo.owner.avatarUrl)
                        .placeholder(R.drawable.octocat)
                        .into(image)
                    repoName.text = repo.name
                    repoOwner.text = repo.owner.login
                    starsCount.text = repo.startGazersCount.toString()
                    languageName.text = repo.language
                    setupContainerClick(cardContainer, repo)
                }
            }
        }

        private fun setupContainerClick(container: View, repository: Repository) {
            itemView.apply {
                container.setDebouncedClickListener {
                    val repoParcel = RepoParcelize(
                        repository.owner.avatarUrl,
                        repository.owner.login,
                        repository.name,
                        repository.description.orEmpty().orEmpty(),
                        repository.url,
                        repository.startGazersCount.toString().orEmpty(),
                        repository.forks,
                        repository.issues.toString().orEmpty(),
                        repository.watchers.toString().orEmpty(),
                        repository.language.orEmpty().orEmpty(),
                    )
                    val directions = when (navDirections) {
                        DETAIL -> HomeFragmentDirections.actionHomeFragmentToRepoDetailFragment(
                            repoParcel
                        )
                        SEARCH -> SearchFragmentDirections.actionSearchFragmentToRepoDetailFragment(
                            repoParcel
                        )
                    }
                    findNavController().navigate(directions)
                }
            }
        }
    }
}
