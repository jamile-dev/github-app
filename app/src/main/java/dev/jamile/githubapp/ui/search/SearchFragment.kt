package dev.jamile.githubapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.Repository
import dev.jamile.githubapp.models.ResponseStatus.*
import dev.jamile.githubapp.ui.MainAdapter
import dev.jamile.githubapp.utils.Directions
import dev.jamile.githubapp.utils.extensions.hideKeyboard
import dev.jamile.githubapp.utils.extensions.setVisibility
import dev.jamile.githubapp.utils.extensions.textChangeAsFlow
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupToolbar()
        initObservers()
    }

    private fun setupSearchView() {
        searchView.apply {
            isIconified = false
            queryHint = getString(R.string.search_repos)
            requestFocus()
            requestFocusFromTouch()
            search(textChangeAsFlow())
            val listener = SearchView.OnCloseListener {
                findNavController().popBackStack()
                activity?.hideKeyboard(this)
                false
            }
            setOnCloseListener(listener)
        }
    }

    private fun setupToolbar() {
        searchToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
                activity?.hideKeyboard(this)
            }
        }

    }

    private fun initObservers() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState.status) {
                LOADING -> {
                    setLoading()
                }
                SUCCESS -> {
                    if (viewState.data != null) {
                        setRecyclerViewList(viewState.data.items)
                    }
                }
                ERROR -> {
                    setupError()
                }
                EMPTY_LIST -> {
                    setupEmptyList()
                }
            }
        }
    }

    private fun setLoading() {
        progressBar.setVisibility(true)
    }

    private fun setRecyclerViewList(list: List<Repository>) {
        progressBar.setVisibility(false)
        errorMessage.setVisibility(false)
        emptyListMessage.setVisibility(false)
        searchRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MainAdapter(
                requireContext(),
                list,
                R.layout.search_repo_list_item,
                Directions.SEARCH
            )
            activity?.hideKeyboard(this)
        }
    }

    private fun setupError() {
        errorMessage.setVisibility(true)
        progressBar.setVisibility(false)
    }

    private fun setupEmptyList() {
        progressBar.setVisibility(false)
        emptyListMessage.setVisibility(true)
    }

    private fun search(textAsFlow: Flow<String>) {
        viewModel.searchQuery(textAsFlow)
    }
}