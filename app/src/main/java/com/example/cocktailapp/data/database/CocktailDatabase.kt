package com.example.cocktailapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [dbCocktail::class, dbIngredient::class, dbMeasurement::class, CocktailIngredientCrossRef::class], version = 1, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}