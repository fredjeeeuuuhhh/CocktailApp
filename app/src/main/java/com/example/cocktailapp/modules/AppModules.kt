package com.example.cocktailapp.modules

import android.content.Context
import androidx.room.Room
import com.example.cocktailapp.data.CocktailApiRepository
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.IngredientApiRepository
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.data.database.CocktailDao
import com.example.cocktailapp.data.database.CocktailDatabase
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.IngredientApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppRepoModule{
    @Provides
    fun providesCocktailRepository(apiService:CocktailApiService): CocktailRepository =
        CocktailApiRepository(apiService)//vervangen door offline versie
    @Provides
    fun providesIngredientRepository(apiService: IngredientApiService): IngredientRepository =
        IngredientApiRepository(apiService)//vervangen door offline versie
}

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceModule{
    @Provides
    fun providesCocktailApiService(retrofit: Retrofit): CocktailApiService =
        CocktailApiService.create(retrofit)
    @Provides
    fun providesIngredientApiService(retrofit: Retrofit): IngredientApiService =
        IngredientApiService.create(retrofit)
}

