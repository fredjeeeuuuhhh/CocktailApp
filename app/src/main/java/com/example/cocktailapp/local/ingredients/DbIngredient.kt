package com.example.cocktailapp.local.ingredients

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing an Ingredient entity in the Room database.
 *
 * @property ingredientName The name of the ingredient.
 * @property description A description or additional information about the ingredient.
 * @property type The type or category of the ingredient (e.g., "Fruit", "Spirit", "Mixer").
 * @property containsAlcohol A boolean indicating whether the ingredient contains alcohol.
 * @property alcoholPercentage The percentage of alcohol in the ingredient if it contains alcohol.
 * @property thumbnail The URL or path to the thumbnail image representing the ingredient.
 * @property isOwned A boolean indicating whether the user owns this ingredient.
 */
@Entity
data class DbIngredient(
    @PrimaryKey(autoGenerate = false)
    var ingredientName: String,
    var description: String?,
    var type: String?,
    var containsAlcohol: Boolean?,
    var alcoholPercentage: String?,
    var thumbnail: String,
    var isOwned: Boolean,
)
