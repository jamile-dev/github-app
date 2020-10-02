package dev.jamile.githubapp.ui.search

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
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import kotlinx.android.synthetic.main.search_repo_list_item.view.*

class SearchAdapter(
    private val context: Context,
    private val reposList: List<Repository>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.search_repo_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val repo = reposList[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = reposList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repository) {
            itemView?.apply {
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
                setupContainerClick(repo)
            }
        }

        private fun setupContainerClick(repository: Repository) {
            itemView.apply {
                container.setDebouncedClickListener {
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
                        SearchFragmentDirections.actionSearchFragmentToRepoDetailFragment(repoParcel)
                    findNavController().navigate(directions)
                }
            }
        }

    }
}