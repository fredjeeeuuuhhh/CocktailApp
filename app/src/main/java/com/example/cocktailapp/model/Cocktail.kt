package com.example.cocktailapp.model

/**
 * Data class representing a Cocktail domain object.
 *
 * @property id The unique identifier for the cocktail.
 * @property title The name or title of the cocktail.
 * @property category The category of the cocktail (e.g., "Martini", "Margarita").
 * @property alcoholFilter If it contains alcohol, or if it is optional
 * @property typeOfGlass The type of glass used for serving the cocktail.
 * @property instructions Instructions to prepare the cocktail.
 * @property image The URL or path to the image representing the cocktail.
 * @property ingredientNames List of ingredient names used in the cocktail.
 * @property measurements List of corresponding measurements for the ingredients.
 * @property isFavorite Boolean indicating whether the cocktail is marked as a favorite.
 * @property nrOwnedIngredients Number of ingredients owned for this cocktail.
 */
data class Cocktail(
    var id: Int,
    var title: String,
    var category: String? = null,
    var alcoholFilter: String? = null,
    var typeOfGlass: String? = null,
    var instructions: String? = null,
    var image: String,
    var ingredientNames: List<String>? = null,
    var measurements: List<String>? = null,
    var isFavorite: Boolean = false,
    var nrOwnedIngredients: Int = 0,
)
