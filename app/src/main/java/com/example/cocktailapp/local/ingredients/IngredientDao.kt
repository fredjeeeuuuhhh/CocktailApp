package com.example.cocktailapp.local.ingredients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with Ingredient-related entities in the Room database.
 */
@Dao
interface IngredientDao {

    /**
     * Inserts a new ingredient into the database. If a conflict arises with an existing ingredient, it ignores the insertion.
     *
     * @param ingredient the ingredient to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: DbIngredient)

    /**
     * Inserts an ingredient into the database if it doesn't already exist.
     *
     * @param ingredient the ingredient to be inserted
     */
    @Transaction
    suspend fun insertIngredientIfNotExisting(ingredient: DbIngredient) {
        val existingIngredient = getIngredientByName(ingredient.ingredientName)
        if (existingIngredient == null) {
            insertIngredient(ingredient)
        }
    }

    /**
     * Retrieves an ingredient by its name from the database.
     *
     * @param name The name of the ingredient to retrieve.
     * @return The [DbIngredient] object representing the ingredient in the database.
     */
    @Query("SELECT * FROM dbingredient WHERE ingredientname LIKE :name")
    suspend fun getIngredientByName(name: String): DbIngredient

    /**
     * Updates the details of an ingredient in the database.
     *
     * @param name The name of the ingredient to update.
     * @param desc The description or additional information about the ingredient.
     * @param alcohol A boolean indicating whether the ingredient contains alcohol.
     * @param percentage The percentage of alcohol in the ingredient if it contains alcohol.
     * @param type The type or category of the ingredient (e.g., "Fruit", "Spirit", "Mixer").
     */
    @Query("UPDATE DbIngredient SET description = :desc, containsAlcohol = :alcohol, alcoholPercentage = :percentage, type = :type WHERE ingredientName LIKE :name")
    suspend fun updateIngredient(name: String, desc: String, alcohol: Boolean, percentage: String, type: String)

    /**
     * Updates the 'isOwned' status of an ingredient in the database.
     *
     * @param ingredientName The name of the ingredient to update.
     * @param isOwned Boolean indicating whether the user owns this ingredient.
     */
    @Query("UPDATE dbingredient SET isOwned = :isOwned WHERE ingredientname LIKE :ingredientName")
    suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean)

    /**
     * Retrieves all ingredients from the database ordered by 'isOwned' status, returning as a Flow of lists.
     *
     * @return A [Flow] of [List] of [DbIngredient] representing all ingredients in the database.
     */
    @Query("SELECT * FROM dbingredient ORDER BY isOwned DESC")
    fun getAll(): Flow<List<DbIngredient>>

    /**
     * Retrieves an ingredient by its name as a Flow from the database.
     *
     * @param ingredientName The name of the ingredient to retrieve.
     * @return A [Flow] of [DbIngredient] representing the ingredient in the database.
     */
    @Query("SELECT * FROM dbingredient WHERE ingredientname LIKE :ingredientName")
    fun getByName(ingredientName: String): Flow<DbIngredient>
}
