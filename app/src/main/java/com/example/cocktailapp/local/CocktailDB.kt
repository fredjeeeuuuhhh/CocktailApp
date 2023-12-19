package com.example.cocktailapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.cocktails.DbCocktail
import com.example.cocktailapp.local.ingredients.DbIngredient
import com.example.cocktailapp.local.ingredients.IngredientDao

@Database(
    entities = [
        DbCocktail::class,
        DbIngredient::class,
        CrossRef::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class CocktailDB : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun IngredientDao(): IngredientDao

    companion object {
        @Volatile
        private var Instance: CocktailDB? = null

        fun getDatabase(context: Context): CocktailDB {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CocktailDB::class.java, "cocktail_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
