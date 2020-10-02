package dev.jamile.githubapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.Repository
import dev.jamile.githubapp.models.ResponseStatus
import dev.jamile.githubapp.utils.extensions.hideKeyboard
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
        initObserver()
    }

    private fun setupSearchView() {
        searchView.apply {
            isIconified = false
            queryHint = "Buscar repositórios"
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

    private fun initObserver() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, { viewState ->
            when (viewState.status) {
                ResponseStatus.LOADING -> {
                    setLoading()
                }
                ResponseStatus.SUCCESS -> {
                    setRecyclerViewList(viewState.data!!.items)
                }
                ResponseStatus.ERROR -> {
                    setupError(viewState.error)
                }
                ResponseStatus.EMPTY_LIST -> {
                    setupEmptyList()
                }
            }
        })
    }

    private fun setLoading() {
//        TODO("Criar extension para visibility")
        progressBar.visibility = View.VISIBLE
    }

    private fun setRecyclerViewList(list: List<Repository>) {
        // TODO("Melhorar isso aqui tbm")
        progressBar.visibility = View.GONE
        searchRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SearchAdapter(requireContext(), list)
        }
    }

    private fun setupError(error: Throwable?) {
        Toast.makeText(requireContext(), error?.message ?: "Erro desconhecido", Toast.LENGTH_SHORT)
            .show()
        progressBar.visibility = View.GONE
    }

    private fun setupEmptyList() {
        emptyListMessage.visibility = View.VISIBLE
    }


    private fun setupEmptyState() {
        // TODO("Criar texto")
    }


    private fun search(textAsFlow: Flow<String>) {
        viewModel.searchQuery(textAsFlow)
    }


}