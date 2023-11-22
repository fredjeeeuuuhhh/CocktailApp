package com.example.cocktailapp.model

data class Cocktail(
    var id: Int,
    var title: String,
    var category: String,
    var alcoholFilter: String,
    var typeOfGlass: String,
    var instructions: String,
    var image: String,
    var ingredients: List<Ingredient>,
    var measurements: List<String>,
    var isFavorite: Boolean,
)
