package dev.jamile.githubapp.ui.home

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
import dev.jamile.githubapp.ui.search.SearchFragmentDirections
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import kotlinx.android.synthetic.main.repo_item_layout.view.*

class HomeListAdapter(
    private val context: Context,
    private val reposList: List<Repository>
) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.repo_item_layout, parent, false)
        )

    override fun onBindViewHolder(holder: HomeListAdapter.ViewHolder, position: Int) {
        val repo = reposList[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = reposList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repository) {
            itemView.apply {
                Glide
                    .with(itemView)
                    .load(repo.owner.avatarUrl)
                    .placeholder(R.drawable.octocat)
                    .into(image)
                repoName.text = repo.name
                repoOwner.text = repo.owner.login
                starsCount.text = repo.startGazersCount.toString()
                languageName.text = repo.language
                navigateToRepoDetail(repo)
            }
        }

        private fun navigateToRepoDetail(repository: Repository) {
            itemView.apply {
                cardContainer.setDebouncedClickListener {
                    val repoParcel = RepoParcelize(
                        repository.owner.avatarUrl,
                        repository.owner.login,
                        repository.name,
                        repository.description.orEmpty(),
                        repository.url,
                        repository.startGazersCount.toString().orEmpty(),
                        repository.forks,
                        repository.issues.toString().orEmpty(),
                        repository.watchers.toString().orEmpty(),
                        repository.language.orEmpty(),
                    )
                    val directions =
                        HomeFragmentDirections.actionHomeFragmentToRepoDetailFragment(repoParcel)
                    findNavController().navigate(directions)
                }
            }
        }
    }

}