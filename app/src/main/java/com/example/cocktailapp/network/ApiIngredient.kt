package com.example.cocktailapp.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiIngredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String,
    val strType: String,
    val strAlcohol: String,
    val strABV: String?,
)
@Serializable
data class ApiIngredientName(
    val strIngredient1: String,
)
@Serializable
data class ApiIngredientNames(
    val drinks: List<ApiIngredientName>
)

@Serializable
data class ApiIngredientList(
    val ingredients: List<ApiIngredient>
)
