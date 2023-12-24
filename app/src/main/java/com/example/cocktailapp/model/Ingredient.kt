package com.example.cocktailapp.model

/**
 * Data class representing an Ingredient domain object.
 *
 * @property name The name of the ingredient.
 * @property description A description or additional information about the ingredient.
 * @property type The type or category of the ingredient (e.g., "Fruit", "Spirit", "Mixer").
 * @property containsAlcohol A boolean indicating whether the ingredient contains alcohol.
 * @property alcoholPercentage The percentage of alcohol in the ingredient if it contains alcohol.
 * @property thumbnail The URL or path to the thumbnail image representing the ingredient.
 * @property isOwned Boolean indicating whether the user owns this ingredient.
 */
data class Ingredient(
    var name: String,
    var description: String? = null,
    var type: String? = null,
    var containsAlcohol: Boolean? = null,
    var alcoholPercentage: String? = null,
    var thumbnail: String,
    var isOwned: Boolean? = null,
)
