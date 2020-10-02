package dev.jamile.githubapp.ui.home

import dev.jamile.githubapp.models.Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jamile.githubapp.R
import dev.jamile.githubapp.models.ResponseStatus.*
import dev.jamile.githubapp.utils.extensions.setDebouncedClickListener
import kotlinx.android.synthetic.main.fragment_home.*
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
//        TODO("Criar extension para visibility")
        progressBar.visibility = View.VISIBLE
    }

    private fun setRecyclerViewList(list: List<Repository>) {
        // TODO("Melhorar isso aqui tbm")
        progressBar.visibility = View.GONE
        recyclerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeListAdapter(requireContext(), list)
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
}