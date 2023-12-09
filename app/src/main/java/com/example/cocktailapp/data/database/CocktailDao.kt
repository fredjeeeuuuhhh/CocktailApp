package com.example.cocktailapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    @Insert(entity = DbCocktail::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbCocktail):Long

    /**
     * Update the favorite status of a cocktail
     *
     * @param cocktailId id of the cocktail
     * @param isFavorite status to be updated
     */
    @Query("UPDATE cocktails SET is_favorite = :isFavorite WHERE cocktailId = :cocktailId")
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Int

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
    fun getItemM(id: Int): CocktailWithMeasurements
    @Transaction
    @Query("SELECT * from cocktails WHERE cocktailId = :id")
    fun getItemI(id: Int): CocktailWithIngredientNames
    @Transaction
    @Query("SELECT * from ingredients WHERE name = :name")
    suspend fun getCocktailIdsContainingIngredientName(name: String): IngredientWithCocktails

    /**
     * Observes list of cocktails.
     *
     * @return all cocktails.
     */
    @Query("SELECT * from cocktails ORDER BY is_favorite DESC")
    fun getAllItems(): Flow<List<DbCocktail>>
    @Query("SELECT * from cocktails WHERE category = :category ORDER BY is_favorite DESC")
    fun getAllItemsInCategory(category: String): Flow<List<DbCocktail>>
    @Transaction
    suspend fun insertCocktailWithMeasurementsAndIngredientNames(cocktail:DbCocktail, measurements:List<DbMeasurement>,ingredientNames: List<DbIngredientName>){
        val cocktailId = insert(cocktail)
        val measurementsWithCocktailId = measurements.map {
            it.copy(measurementOwnerId = cocktailId.toInt())
        }
        insertMeasurements(measurementsWithCocktailId)
        val ingredientNamesWithCocktailId = ingredientNames.map {
            it.copy(ingredientNameOwnerId = cocktailId.toInt())
        }
        insertIngredientNames(ingredientNamesWithCocktailId)
        for(ingredient in ingredientNames){
            linkIngredientToCocktail(cocktailId.toInt(),ingredient.name)
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