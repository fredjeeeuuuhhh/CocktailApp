package com.example.cocktailapp.modules


import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.data.OfflineCocktailsRepository
import com.example.cocktailapp.data.OfflineIngredientsRepository
import com.example.cocktailapp.data.database.CocktailDao
import com.example.cocktailapp.data.database.IngredientDao
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
    fun providesCocktailRepository(cocktailDao: CocktailDao, cocktailApiService: CocktailApiService): CocktailRepository =
        OfflineCocktailsRepository(cocktailDao, cocktailApiService)
    @Provides
    fun providesIngredientRepository(ingredientDao: IngredientDao, cocktailDao: CocktailDao, ingredientApiService: IngredientApiService): IngredientRepository =
        OfflineIngredientsRepository( ingredientDao, cocktailDao, ingredientApiService)
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

