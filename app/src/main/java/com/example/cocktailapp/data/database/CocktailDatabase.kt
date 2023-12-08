package com.example.cocktailapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbCocktail::class, DbIngredient::class, DbMeasurement::class, CocktailIngredientCrossRef::class], version = 1, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun ingredientDao(): IngredientDao
}