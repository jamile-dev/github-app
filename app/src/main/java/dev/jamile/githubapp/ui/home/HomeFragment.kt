package dev.jamile.githubapp.ui.home

import dev.jamile.githubapp.models.Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.RepoParcelize
import dev.jamile.githubapp.models.ResponseStatus.*
import dev.jamile.githubapp.ui.search.SearchFragmentDirections
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import dev.jamile.githubapp.utils.extensions.setVisibility
import dev.jamile.githubapp.utils.extensions.setVisibilityIfNeeded
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.emptyListMessage
import kotlinx.android.synthetic.main.fragment_home.progressBar
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.repo_item_layout.*
import kotlinx.android.synthetic.main.search_repo_list_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getRepositoriesList()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        navigateToSearch()
    }

    private fun navigateToSearch() {
        searchIcon.setDebouncedClickListener {
            findNavController(this).navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun initObservers() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, { viewState ->
            when (viewState.status) {
                LOADING -> {
                    setLoading()
                }
                SUCCESS -> {
                    setRecyclerViewList(viewState.data!!.items)
                }
                ERROR -> {
                    setupError(viewState.error)
                }
                EMPTY_LIST -> {
                    setupEmptyList()
                }
            }
        })
    }

    private fun setLoading() {
        progressBar.setVisibility(true)
        searchRecycler?.setVisibility(false)
    }

    private fun setRecyclerViewList(list: List<Repository>) {
        progressBar.setVisibility(false)
        recyclerList.apply {
            setVisibility(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeListAdapter(requireContext(), list)
            val controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_down);
            layoutAnimation = controller;
            adapter?.notifyDataSetChanged();
            scheduleLayoutAnimation();
        }
    }

    private fun setupError(error: Throwable?) {
        progressBar.setVisibility(false)
        recyclerList.setVisibility(false)
        Toast.makeText(requireContext(), error?.message ?: "Erro desconhecido", Toast.LENGTH_SHORT)
            .show()
    }

    private fun setupEmptyList() {
        emptyListMessage.setVisibility(true)
        progressBar.setVisibility(false)
        recyclerList.setVisibility(false)
    }
}