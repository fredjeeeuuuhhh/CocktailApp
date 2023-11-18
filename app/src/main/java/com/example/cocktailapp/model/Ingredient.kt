package com.example.cocktailapp.model

data class Ingredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription:  String,
    val strType: String,
    val strAlcohol: String,
    val strABV: String?,
    val strIngredientThumb: String,
    var isOwned: Boolean,
)
