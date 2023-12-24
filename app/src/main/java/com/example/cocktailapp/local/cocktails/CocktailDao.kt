package com.example.cocktailapp.local.cocktails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
/**
 * Data Access Object (DAO) interface for interacting with Cocktail-related entities in the Room database.
 */
@Dao
interface CocktailDao {
    /**
     * Inserts a new cocktail into the database. If a conflict arises with an existing cocktail, it ignores the insertion.
     *
     * @param cocktail the cocktail to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCocktail(cocktail: DbCocktail)

    /**
     * Inserts a cross-reference between a cocktail and an ingredient into the database. If a conflict arises, it ignores the insertion.
     *
     * @param ref the cross reference to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(ref: CrossRef)

    /**
     * Inserts a cocktail into the database if it doesn't already exist.
     *
     * @param cocktail the cocktail to be inserted
     */
    @Transaction
    suspend fun insertCocktailIfNotExisting(cocktail: DbCocktail) {
        val existingCocktail = getCocktailById(cocktail.cocktailId)
        if (existingCocktail == null) {
            insertCocktail(cocktail)
        }
    }

    /**
     * Updates the number of owned ingredients for a specified cocktail.
     *
     * @param id the id of the cocktail to be updated.
     */
    @Transaction
    suspend fun updateTransaction(id: Int) {
        val nrOfOwned = getOwnedIngredientsForCocktail(id)
        updateNrOfOwned(id, nrOfOwned)
    }

    /**
     * Updates the number of owned ingredients for a specified cocktail in the database.
     *
     * @param cocktailId the id of the cocktail to be updated
     * @param nrOfOwned the new number of owned ingredients for a cocktail
     */
    @Query("UPDATE dbcocktail SET nrOfOwnedIngredients = :nrOfOwned WHERE cocktailId = :cocktailId")
    suspend fun updateNrOfOwned(cocktailId: Int, nrOfOwned: Int)

    /**
     * Updates the 'isFavorite' status for a specified cocktail in the database.
     *
     * @param cocktailId the id of the cocktail to be updated
     * @param isFavorite the boolean indicating whether a cocktail is marked as favorite by the user
     */
    @Query("UPDATE dbcocktail SET isFavorite = :isFavorite WHERE cocktailId = :cocktailId")
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean)

    /**
     * Retrieves cocktails associated with a particular ingredient.
     *
     * @param ingredientName the name of the ingredient
     * @return a [List] of [Int] representing the cocktail ID's
     */
    @Query("SELECT cocktailId FROM DbCocktail WHERE cocktailId IN (SELECT cocktailId FROM CrossRef WHERE ingredientName LIKE :ingredientName)")
    suspend fun getByIngredientIdOnlyNoFlow(ingredientName: String): List<Int>

    /**
     * Retrieves a specific cocktail by its ID.
     *
     * @param id the id of the cocktail to be retrieved
     * @return the retrieved [DbCocktail] object
     */
    @Query("SELECT * FROM dbcocktail WHERE cocktailId = :id")
    suspend fun getCocktailById(id: Int): DbCocktail

    /**
     * Retrieves the number of owned ingredients for a cocktail.
     *
     * @param cocktailId the id of the cocktail
     * @return an [Int] representing the number of owned ingredients
     */
    @Query(
        "SELECT COUNT(ingredientName)\n" +
            "FROM dbingredient\n" +
            "WHERE EXISTS (\n" +
            "    SELECT ingredientName \n" +
            "    FROM CrossRef \n" +
            "    WHERE cocktailId = :cocktailId \n" +
            "    AND REPLACE(REPLACE(ingredientName, ' ', ''), '_', '') IN (\n" +
            "        SELECT REPLACE(REPLACE(ingredientName, ' ', ''), '_', '') \n" +
            "        FROM dbingredient\n" +
            "    )\n" +
            ")\n" +
            "AND isOwned = 1",
    )
    suspend fun getOwnedIngredientsForCocktail(cocktailId: Int): Int

    /**
     * Retrieves all cocktails from the database ordered by 'isFavorite' status, returning as a Flow of lists.
     *
     * @return a [Flow] of a [List] of [DbCocktail] objects
     */
    @Query("SELECT * FROM dbcocktail ORDER BY isFavorite DESC")
    fun getAll(): Flow<List<DbCocktail>>

    /**
     * Retrieves a specific cocktail by its ID as a Flow.
     *
     * @param cocktailId the id of the cocktail to retrieve
     * @return a [Flow] of a [DbCocktail] object
     */
    @Query("SELECT * FROM dbcocktail WHERE cocktailId = :cocktailId")
    fun getById(cocktailId: Int): Flow<DbCocktail>

    /**
     * Retrieves cocktails associated with a particular ingredient, returned as a Flow.
     *
     * @param ingredientName the name of an ingredient
     * @return a [Flow] of a [List] of [DbCocktail] objects
     */
    @Query("SELECT * FROM DbCocktail WHERE cocktailId IN (SELECT cocktailId FROM CrossRef WHERE ingredientName LIKE :ingredientName)")
    fun getByIngredient(ingredientName: String): Flow<List<DbCocktail>>
}
