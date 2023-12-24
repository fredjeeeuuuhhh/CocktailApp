package com.example.cocktailapp.viewmodeltests

import com.example.cocktailapp.fake.FakeApiIngredientRepository
import com.example.cocktailapp.helpers.TestDispatcherRule
import com.example.cocktailapp.ui.IngredientApiState
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.IngredientsOverviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelIngredientOverviewTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: IngredientsOverviewViewModel

    @Before
    fun initializeViewModel() {
        viewModel = IngredientsOverviewViewModel(
            ingredientRepository = FakeApiIngredientRepository(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `private loadIngredients updates ingredientApiState`() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        initializeViewModel()

        assert(viewModel.ingredientApiState is IngredientApiState.Loading)

        // Execute pending coroutines actions
        advanceUntilIdle()

        assert(viewModel.ingredientApiState is IngredientApiState.Succes)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `refreshIngredients updates isRefreshing`() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel.refreshIngredients()

        assert(viewModel.uiState.value.isRefreshing)

        // Execute pending coroutines actions
        advanceUntilIdle()

        assert(!viewModel.uiState.value.isRefreshing)
    }
}
