package dev.jamile.githubapp.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.Repository
import kotlinx.android.synthetic.main.repo_item_layout.view.*
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
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.octocat)
                    .into(authorImage)
                ownerName.text = repo.owner.login
                repositoryName.text = repo.name
                repoDescription.text = repo.description
                stars.text = repo.startGazersCount.toString()
                langName.text = repo.language
            }
        }
    }

}