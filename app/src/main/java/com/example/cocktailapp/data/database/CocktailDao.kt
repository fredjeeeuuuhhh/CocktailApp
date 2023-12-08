package com.example.cocktailapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurements(measurements: List<DbMeasurement>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredientNames(ingredientNames: List<DbIngredientName>)
    @Insert
    suspend fun insertCrossRef(crossRef: CocktailIngredientCrossRef)
    /**
     * Insert or update a cocktail in the database. If a cocktail already exists, replace it.
     *
     * @param item the cocktail to be inserted or updated.
     * @return int of upserted cocktail
     */
    @Upsert
    suspend fun upsert(item: DbCocktail):Int

    /**
     * Update the favorite status of a cocktail
     *
     * @param cocktailId id of the cocktail
     * @param isFavorite status to be updated
     */
    @Query("UPDATE cocktails SET is_favorite = :isFavorite WHERE cocktailId = :cocktailId")
    fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<DbCocktail>

    /**
     * Observes a single cocktail.
     *
     * @param id the cocktail id.
     * @return the cocktail with cocktailId.
     */
    @Query("SELECT * from cocktails WHERE cocktailId = :id")
    fun getItem(id: Int): Flow<DbCocktail>
    @Transaction
    @Query("SELECT * from cocktails WHERE cocktailId = :id")
    suspend fun getItemM(id: Int): CocktailWithMeasurements
    @Transaction
    @Query("SELECT * from cocktails WHERE cocktailId = :id")
    suspend fun getItemI(id: Int): CocktailWithIngredientNames
    @Transaction
    @Query("SELECT * from cocktailingredientcrossref WHERE name = :name")
    suspend fun getCocktailIdsContainingIngredientName(name: String): IngredientWithCocktails

    /**
     * Observes list of cocktails.
     *
     * @return all cocktails.
     */
    @Query("SELECT cocktailId, title, image from cocktails GROUP BY is_favorite")
    fun getAllItems(): Flow<List<DbCocktail>>
    @Query("SELECT cocktailId, title, image from cocktails WHERE category = :category GROUP BY is_favorite")
    fun getAllItemsInCategory(category: String): Flow<List<DbCocktail>>
    @Transaction
    suspend fun insertCocktailWithMeasurementsAndIngredientNames(cocktail:DbCocktail, measurements:List<DbMeasurement>,ingredientNames: List<DbIngredientName>){
        val cocktailId = upsert(cocktail)
        val measurementsWithCocktailId = measurements.map {
            it.copy(measurementOwnerId = cocktailId)
        }
        insertMeasurements(measurementsWithCocktailId)
        val ingredientNamesWithCocktailId = ingredientNames.map {
            it.copy(ingredientNameOwnerId = cocktailId)
        }
        insertIngredientNames(ingredientNamesWithCocktailId)
        for(ingredient in ingredientNames){
            linkIngredientToCocktail(ingredient.ingredientNameOwnerId,ingredient.name)
        }
    }

    @Transaction
    suspend fun getCocktailsWithIngredient(ingredientName: String):  List<DbCocktail>{
        return  getCocktailIdsContainingIngredientName(ingredientName).ingredients
    }

    @Transaction
    suspend fun linkIngredientToCocktail(cocktailId:Int, ingredientName:String){
       insertCrossRef(CocktailIngredientCrossRef(cocktailId, ingredientName))
    }
}