package com.example.cocktailapp.modules

import android.content.Context
import androidx.room.Room
import com.example.cocktailapp.data.database.CocktailDao
import com.example.cocktailapp.data.database.CocktailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): CocktailDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CocktailDatabase::class.java,
            "Cocktails.db"
        ).build()
    }

    @Provides
    fun provideCocktailDao(database: CocktailDatabase): CocktailDao = database.cocktailDao()
}