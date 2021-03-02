package dev.jamile.githubapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.jamile.githubapp.base.ViewState
import dev.jamile.githubapp.models.ResponseStatus
import dev.jamile.githubapp.models.SearchResponse
import dev.jamile.githubapp.network.Result
import dev.jamile.githubapp.repository.ReposRepository
import dev.jamile.githubapp.testUtils.TestCoroutineContextProvider
import dev.jamile.githubapp.testUtils.createFlowTest
import dev.jamile.githubapp.ui.search.SearchViewModel
import dev.jamile.githubapp.utils.DispatcherProvider
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
class SearchTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchEventObserver: Observer<ViewState<SearchResponse, ResponseStatus>>

    private lateinit var repoRepository: ReposRepository

    private val contextProvider = TestCoroutineContextProvider()

    private lateinit var repoResponse: Result<SearchResponse>

    private val query = "android"

    private val testModule = module {
        single<DispatcherProvider> { TestCoroutineContextProvider() }
    }

    @Before
    fun setup() {
        startKoin { modules(testModule) }

        searchEventObserver = spyk(Observer { })

        repoRepository = mockk()

        repoResponse = mockk()

        viewModel = SearchViewModel(contextProvider, repoRepository).apply {
            searchLiveData.observeForever(searchEventObserver)
        }
    }

    @Test
    fun `WHEN search a query THEN change observer`() = runBlockingTest {
        val flow = createFlowTest(query)

        coEvery {
            repoRepository.searchRepositories(query)
        } returns repoResponse

        viewModel.apply {
            searchQuery(flow)
            searchLiveData.observeForever(searchEventObserver)
        }

        val captor = mutableListOf<ViewState<SearchResponse, ResponseStatus>>()
        verify { searchEventObserver.onChanged(capture(captor)) }
    }

    @Test
    fun `WHEN set empty query THEN dont call api`() = runBlockingTest {
        val emptyStringFlow = createFlowTest("")
        viewModel.searchQuery(emptyStringFlow)
        verify { repoRepository wasNot Called }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}