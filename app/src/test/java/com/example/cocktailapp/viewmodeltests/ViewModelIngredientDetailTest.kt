package com.example.cocktailapp.viewmodeltests

import androidx.lifecycle.SavedStateHandle
import com.example.cocktailapp.fake.FakeApiCocktailRepository
import com.example.cocktailapp.fake.FakeApiIngredientRepository
import com.example.cocktailapp.helpers.TestDispatcherRule
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.IngredientDetailApiState
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.IngredientDetailViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ViewModelIngredientDetailTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun `clicking Circle updates Ingredient object`() {
        val viewModel = IngredientDetailViewModel(
            cocktailRepository = FakeApiCocktailRepository(),
            ingredientRepository = FakeApiIngredientRepository(),
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    CocktailDestinationsArgs.INGREDIENT_NAME_ARG to "Cola",
                ),
            ),
        )

        val ingredientValue = { if (viewModel.ingredientDetailApiState is IngredientDetailApiState.Succes) (viewModel.ingredientDetailApiState as IngredientDetailApiState.Succes).ingredient else null }

        viewModel.onOwnedChanged(false)

        Assert.assertEquals(false, ingredientValue()!!.isOwned)
    }
}
