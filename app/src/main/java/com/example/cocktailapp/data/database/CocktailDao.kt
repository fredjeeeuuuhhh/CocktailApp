package com.example.cocktailapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    //wat met joined tables??

    /**
     * Insert or update a cocktail in the database. If a cocktail already exists, replace it.
     *
     * @param item the cocktail to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(item: dbCocktail)

    /**
     * Update the favorite status of a cocktail
     *
     * @param cocktailId id of the cocktail
     * @param isFavorite status to be updated
     */
    @Query("UPDATE cocktails SET is_favorite = :isFavorite WHERE cocktailId = :cocktailId")
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean)

    /**
     * Observes a single cocktail.
     *
     * @param id the cocktail id.
     * @return the cocktail with cocktailId.
     */
    @Query("SELECT * from cocktails WHERE cocktailId = :id")
    fun getItem(id: Int): Flow<dbCocktail>

    /**
     * Observes list of cocktails.
     *
     * @return all cocktails.
     */
    @Query("SELECT cocktailId, title, image from cocktails GROUP BY is_favorite")
    fun getAllItems(): Flow<List<dbCocktail>>

    @Query("SELECT * FROM cocktails WHERE cocktailId = :randomId")
    fun getRandomItem(randomId:Int): Flow<dbCocktail>
}