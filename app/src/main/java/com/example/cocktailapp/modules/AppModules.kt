package com.example.cocktailapp.modules

import com.example.cocktailapp.data.CocktailApiRepository
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.IngredientApiRepository
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.IngredientApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class AppRepoModule{
    @Provides
    fun providesCocktailRepository(apiService:CocktailApiService): CocktailRepository =
        CocktailApiRepository(apiService)
    @Provides
    fun providesIngredientRepository(apiService: IngredientApiService): IngredientRepository =
        IngredientApiRepository(apiService)
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