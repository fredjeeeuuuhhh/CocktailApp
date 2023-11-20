package com.example.cocktailapp.model

data class Cocktail(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String,
    val strAlcoholic: String,
    val strGlass: String,
    val strInstructions: String,
    val strDrinkThumb: String,
    val ingredients: List<String>,
    val measurements: List<String>,
    var isFavorite: Boolean,
)
