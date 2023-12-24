package com.example.cocktailapp.fake

import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.cocktails.DbCocktail
import com.example.cocktailapp.local.ingredients.DbIngredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCocktailDao(
    initialCocktails: List<DbCocktail> = emptyList(),
    initialIngredients: List<DbIngredient> = emptyList(),
) : CocktailDao {
    private lateinit var _cocktails: MutableMap<Int, DbCocktail>
    private var crossrefs: List<CrossRef> = emptyList()
    private var ingredients: List<DbIngredient> = emptyList()
    var cocktails: List<DbCocktail>
        get() = _cocktails.values.toList()
        set(newSupplements) {
            _cocktails = newSupplements.associateBy { it.cocktailId }.toMutableMap()
        }

    init {
        cocktails = initialCocktails
        ingredients = initialIngredients
    }
    override suspend fun insertCocktail(cocktail: DbCocktail) {
        this.cocktails = this.cocktails.plus(cocktail)
    }

    override suspend fun insertCrossRef(ref: CrossRef) {
        crossrefs = crossrefs.plus(ref)
    }

    override suspend fun updateNrOfOwned(cocktailId: Int, nrOfOwned: Int) {
        cocktails = cocktails.map {
            if (it.cocktailId == cocktailId) {
                it.copy(nrOfOwnedIngredients = nrOfOwned)
            } else {
                it
            }
        }
    }

    override suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean) {
        cocktails = cocktails.map {
            if (it.cocktailId == cocktailId) {
                it.copy(isFavorite = isFavorite)
            } else {
                it
            }
        }
    }

    override suspend fun getByIngredientIdOnlyNoFlow(ingredientName: String): List<Int> {
        return cocktails.filter { it.ingredientNames.contains(ingredientName) }.map { it.cocktailId }
    }

    override suspend fun getCocktailById(id: Int): DbCocktail {
        return cocktails.find { it.cocktailId == id }!!
    }

    override suspend fun getOwnedIngredientsForCocktail(cocktailId: Int): Int {
        val names = cocktails.find{ it.cocktailId == cocktailId}!!.ingredientNames
        val ingredients = ingredients.filter { names.contains(it.ingredientName) }
        return ingredients.filter { it.isOwned }.size
    }

    override fun getAll(): Flow<List<DbCocktail>> = flow {
        emit(cocktails)
    }

    override fun getById(cocktailId: Int): Flow<DbCocktail> = flow {
       emit(cocktails.find { it.cocktailId == cocktailId }!!)
    }

    override fun getByIngredient(ingredientName: String): Flow<List<DbCocktail>> = flow{
        emit(cocktails.filter { it.ingredientNames.contains(ingredientName) })
    }
}
