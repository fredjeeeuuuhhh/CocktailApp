package com.example.cocktailapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.local.cocktails.DbCocktail
import com.example.cocktailapp.local.cocktails.DbCocktailDbIngredientCrossRef
import com.example.cocktailapp.local.ingredients.DbIngredient
import com.example.cocktailapp.local.ingredients.IngredientDao

@Database(
    entities = [
        DbCocktail::class,
        DbIngredient::class,
        DbCocktailDbIngredientCrossRef::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class CocktailDB: RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun IngredientDao(): IngredientDao
}