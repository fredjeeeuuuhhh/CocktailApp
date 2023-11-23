package com.example.cocktailapp.model

data class Cocktail(
    var id: Int,
    var title: String,
    var category: String?=null,
    var alcoholFilter: String?=null,
    var typeOfGlass: String?=null,
    var instructions: String?=null,
    var image: String,
    var ingredients: List<Ingredient>?=null,
    var ingredientNames: List<String>?=null,
    var measurements: List<String>?=null,
    var isFavorite: Boolean?=null,
)
