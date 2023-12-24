package com.example.cocktailapp.fake

import com.example.cocktailapp.network.ApiIngredientLookupResult
import com.example.cocktailapp.network.ApiIngredientName
import com.example.cocktailapp.network.ApiIngredientNames
import com.example.cocktailapp.network.IngredientApiService

class FakeIngredientApiService : IngredientApiService {
    override suspend fun getIngredients(): ApiIngredientNames {
        return ApiIngredientNames(FakeDataSource.ingredients.map { ApiIngredientName(it.strIngredient) })
    }

    override suspend fun getIngredientByName(name: String): ApiIngredientLookupResult {
        return ApiIngredientLookupResult(FakeDataSource.ingredients)
    }
}
