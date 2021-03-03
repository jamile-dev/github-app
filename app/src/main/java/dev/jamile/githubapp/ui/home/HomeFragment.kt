package dev.jamile.githubapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.Repository
import dev.jamile.githubapp.models.ResponseStatus.*
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import dev.jamile.githubapp.utils.extensions.setVisibility
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRepositoriesList()
        initObservers()
        navigateToSearch()
    }

    private fun initObservers() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, { viewState ->
            when (viewState.status) {
                LOADING -> {
                    setLoading()
                }
                SUCCESS -> {
                    viewState.data?.let { repos ->
                        setRecyclerViewList(repos.items)
                    }
                }
                ERROR -> {
                    setupError()
                }
                EMPTY_LIST -> {
                    setupEmptyList()
                }
            }
        })
    }

    private fun navigateToSearch() {
        searchIcon.setDebouncedClickListener {
            findNavController(this).navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setLoading() {
        progressBar.setVisibility(true)
    }

    private fun setRecyclerViewList(list: List<Repository>) {
        progressBar.setVisibility(false)
        recyclerList?.apply {
            setVisibility(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeListAdapter(requireContext(), list)
        }
    }

    private fun setupError() {
        progressBar.setVisibility(false)
        recyclerList.setVisibility(false)
        errorMessage.setVisibility(true)
    }

    private fun setupEmptyList() {
        emptyListMessage.setVisibility(true)
        progressBar.setVisibility(false)
        recyclerList.setVisibility(false)
    }
}