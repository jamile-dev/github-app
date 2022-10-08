package dev.jamile.githubapp.ui.repo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.RepoParcelize
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import kotlinx.android.synthetic.main.fragment_repo_detail.*


class RepoDetailFragment : Fragment() {

    private val args by navArgs<RepoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoDetailToolbar.apply {
            setHasOptionsMenu(true)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        bindView(args.repository)
    }

    private fun bindView(repository: RepoParcelize) {
        Glide
            .with(this)
            .load(repository.repoImage)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.octocat)
            .into(repoDetailImage)
        repoDetailOwnerName.text = repository.repoOwnerName
        repoDetailName.text = repository.repoName
        repoDetailDesc.text = repository.repoDescription
        repoDetailUrl.apply {
            text = repository.repoUrl
            setDebouncedClickListener {
                openRepoSite(repository.repoUrl)
            }
        }
        repoDetailForksCount.text = repository.repoForks
        repoDetailIssues.text = repository.repoIssues
        repoDetailStars.text = repository.repoStars
        repoDetailWatchers.text = repository.repoWatchers
    }

    private fun openRepoSite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
