package com.example.cocktailapp.local.ingredients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<DbIngredient>)

    @Upsert
    suspend fun upsertIngredient(ingredient: DbIngredient)

    @Query("UPDATE dbingredient SET isOwned = :isOwned WHERE ingredientname LIKE :ingredientName")
    suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean)

    @Query("SELECT * FROM dbingredient ORDER BY isOwned DESC")
    fun getAll(): Flow<List<DbIngredient>>

    @Query("SELECT * FROM dbingredient WHERE ingredientname LIKE :ingredientName")
    fun getByName(ingredientName: String): Flow<DbIngredient>
}
