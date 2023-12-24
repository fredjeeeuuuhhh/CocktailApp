package com.example.cocktailapp.viewmodeltests

import com.example.cocktailapp.fake.FakeApiCocktailRepository
import com.example.cocktailapp.helpers.TestDispatcherRule
import com.example.cocktailapp.ui.CocktailApiState
import com.example.cocktailapp.ui.cocktails.cocktailoverview.CocktailOverviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelCocktailOverviewTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: CocktailOverviewViewModel

    @Before
    fun initializeViewModel() {
        viewModel = CocktailOverviewViewModel(
            cocktailRepository = FakeApiCocktailRepository(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `private loadCocktails updates cocktailApiState`() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        initializeViewModel()

        assert(viewModel.cocktailApiState is CocktailApiState.Loading)

        // Execute pending coroutines actions
        advanceUntilIdle()

        assert(viewModel.cocktailApiState is CocktailApiState.Succes)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `refreshCocktails updates isRefreshing`() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel.refreshCocktails()

        assert(viewModel.uiState.value.isRefreshing)

        // Execute pending coroutines actions
        advanceUntilIdle()

        assert(!viewModel.uiState.value.isRefreshing)
    }
}
