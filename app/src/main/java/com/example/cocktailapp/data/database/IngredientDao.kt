package com.example.cocktailapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    /**
     * Insert or update an ingredient in the database. If an ingredient already exists, replace it.
     *
     * @param item the ingredient to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(item: DbIngredient)

    /**
     * Update the owned status of a cocktail
     *
     * @param ingredientId id of the ingredient
     * @param isOwned status to be updated
     */
    @Query("UPDATE ingredients SET is_owned = :isOwned WHERE ingredientId = :ingredientId")
    fun updateIsOwned(ingredientId: Int, isOwned: Boolean): Flow<DbIngredient>

    /**
     * Observes a single ingredient.
     *
     * @param name the ingredients name.
     * @return the ingredient with ingredientId.
     */
    @Query("SELECT * from ingredients WHERE name = :name")
    fun getItem(name: String): Flow<DbIngredient>

    /**
     * Observes list of ingredients.
     *
     * @return allingredients.
     */
    @Query("SELECT * from ingredients GROUP BY is_owned")
    fun getAllItems(): Flow<List<DbIngredient>>


}