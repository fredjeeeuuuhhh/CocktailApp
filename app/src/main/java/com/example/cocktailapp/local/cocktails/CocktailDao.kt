package com.example.cocktailapp.local.cocktails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCocktails(cocktails: List<DbCocktail>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(ref: CrossRef)
    @Query("UPDATE dbcocktail SET isFavorite = :isFavorite WHERE cocktailId = :cocktailId")
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM dbcocktail ORDER BY isFavorite DESC")
    fun getAll(): Flow<List<DbCocktail>>

    @Query("SELECT * FROM DbCocktail WHERE cocktailId IN (SELECT cocktailId FROM CrossRef WHERE ingredientName LIKE :ingredientName)")
    fun getByIngredient(ingredientName: String): Flow<List<DbCocktail>>

    @Query("SELECT * FROM dbcocktail WHERE cocktailId = :cocktailId")
    fun getById(cocktailId: Int): Flow<DbCocktail>
}
