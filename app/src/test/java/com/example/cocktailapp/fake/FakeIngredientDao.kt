package com.example.cocktailapp.fake

import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.cocktails.DbCocktail
import com.example.cocktailapp.local.ingredients.DbIngredient
import com.example.cocktailapp.local.ingredients.IngredientDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeIngredientDao(
    initialCocktails: List<DbCocktail> = emptyList(),
    initialIngredients: List<DbIngredient> = emptyList(),
) : IngredientDao {
    private lateinit var _ingredients: MutableMap<String, DbIngredient>
    private var crossrefs: List<CrossRef> = emptyList()
    private var cocktails: List<DbCocktail> = emptyList()
    var ingredients: List<DbIngredient>
        get() = _ingredients.values.toList()
        set(newSupplements) {
            _ingredients = newSupplements.associateBy { it.ingredientName }.toMutableMap()
        }

    private fun initializeCrossrefs() {
        cocktails.forEach { c ->
            c.ingredientNames.forEach {
                crossrefs = crossrefs.plus(CrossRef(c.cocktailId, it))
            }
        }
    }

    init {
        cocktails = initialCocktails
        ingredients = initialIngredients
        initializeCrossrefs()
    }

    override suspend fun insertIngredient(ingredient: DbIngredient) {
        ingredients = ingredients.plus(ingredient)
    }

    override suspend fun getIngredientByName(name: String): DbIngredient {
        return ingredients.find { it.ingredientName == name }!!
    }

    override suspend fun updateIngredient(
        name: String,
        desc: String,
        alcohol: Boolean,
        percentage: String,
        type: String,
    ) {
        ingredients.map {
            if (it.ingredientName == name) {
                it.copy(
                    description = desc,
                    containsAlcohol = alcohol,
                    alcoholPercentage = percentage,
                    type = type,
                )
            } else {
                it
            }
        }
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean) {
        ingredients.map {
            if (it.ingredientName == ingredientName) {
                it.copy(
                    isOwned = isOwned,
                )
            } else {
                it
            }
        }
    }

    override fun getAll(): Flow<List<DbIngredient>> = flow {
        emit(ingredients)
    }

    override fun getByName(ingredientName: String): Flow<DbIngredient> = flow {
        emit(ingredients.find { it.ingredientName == ingredientName }!!)
    }
}
