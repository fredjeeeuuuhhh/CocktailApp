package com.example.cocktailapp.viewmodeltests

import androidx.lifecycle.SavedStateHandle
import com.example.cocktailapp.fake.FakeApiCocktailRepository
import com.example.cocktailapp.helpers.TestDispatcherRule
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.CocktailDetailApiState
import com.example.cocktailapp.ui.cocktails.cocktaildetail.CocktailDetailViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ViewModelCocktailDetailTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun `clicking star updates Cocktail object`() {
        val viewModel = CocktailDetailViewModel(
            cocktailRepository = FakeApiCocktailRepository(),
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    CocktailDestinationsArgs.COCKTAIL_ID_ARG to 1,
                ),
            ),
        )

        val ingredientValue = { if (viewModel.cocktailDetailApiState is CocktailDetailApiState.Succes) (viewModel.cocktailDetailApiState as CocktailDetailApiState.Succes).cocktail else null }

        Assert.assertEquals(true, ingredientValue()!!.isFavorite)

        viewModel.onFavoriteChanged(false)

        Assert.assertEquals(false, ingredientValue()!!.isFavorite)
    }
}
