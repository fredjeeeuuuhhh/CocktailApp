package com.example.cocktailapp.model

data class Ingredient(
    var name: String,
    var description: String? = null,
    var type: String? = null,
    var containsAlcohol: Boolean? = null,
    var alcoholPercentage: String? = null,
    var thumbnail: String,
    var isOwned: Boolean? = null,
)
