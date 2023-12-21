package com.example.cocktailapp.local.ingredients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredients(ingredients: List<DbIngredient>)

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertIngredient(ingredient: DbIngredient)

    @Query("UPDATE DbIngredient SET description = :desc, containsAlcohol = :alcohol, alcoholPercentage = :percentage, type = :type WHERE ingredientName LIKE :name")
    suspend fun updateIngredient(name: String, desc: String, alcohol: Boolean, percentage: String, type: String)

    @Query("UPDATE dbingredient SET isOwned = :isOwned WHERE ingredientname LIKE :ingredientName")
    suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean)

    @Query("SELECT * FROM dbingredient ORDER BY isOwned DESC")
    fun getAll(): Flow<List<DbIngredient>>

    @Query("SELECT * FROM dbingredient WHERE ingredientname LIKE :ingredientName")
    fun getByName(ingredientName: String): Flow<DbIngredient>
}
