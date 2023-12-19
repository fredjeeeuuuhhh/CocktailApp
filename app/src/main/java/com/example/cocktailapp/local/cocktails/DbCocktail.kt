package com.example.cocktailapp.local.cocktails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbCocktail(
    @PrimaryKey(autoGenerate = false)
    var cocktailId: Int,
    var title: String,
    var category: String?,
    var alcoholFilter: String?,
    var typeOfGlass: String?,
    var instructions: String?,
    var image: String,
    var ingredientNames: List<String>,
    var measurements: List<String>,
    var isFavorite: Boolean,
)

@Entity(primaryKeys = ["cocktailId", "ingredientName"])
data class CrossRef(
    val cocktailId: Int,
    val ingredientName: String,
)
