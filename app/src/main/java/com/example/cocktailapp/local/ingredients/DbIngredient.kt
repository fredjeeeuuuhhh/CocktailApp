package com.example.cocktailapp.local.ingredients

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbIngredient(
    @PrimaryKey(autoGenerate = false)
    var ingredientName: String,
    var ingredientId: Int?,
    var description: String?,
    var type: String?,
    var containsAlcohol: Boolean?,
    var alcoholPercentage: String?,
    var thumbnail: String,
    var isOwned: Boolean,
)
