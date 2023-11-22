package com.example.cocktailapp.model

data class Ingredient(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val containsAlcohol: Boolean,
    val alcoholPercentage: String?,
    val thumbnail: String,
    var isOwned: Boolean,
)
