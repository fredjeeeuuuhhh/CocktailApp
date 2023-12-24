package com.example.cocktailapp.local.cocktails

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a Cocktail entity in the Room database.
 *
 * @property cocktailId The unique identifier for the cocktail.
 * @property title The name or title of the cocktail.
 * @property category The category of the cocktail (e.g., "Martini", "Margarita").
 * @property alcoholFilter If it contains alcohol, or if it's optional.
 * @property typeOfGlass The type of glass used for serving the cocktail.
 * @property instructions Instructions to prepare the cocktail.
 * @property image The URL or path to the image representing the cocktail.
 * @property ingredientNames List of ingredient names used in the cocktail.
 * @property measurements List of corresponding measurements for the ingredients.
 * @property isFavorite Boolean indicating whether the cocktail is marked as a favorite.
 * @property nrOfOwnedIngredients Number of ingredients owned for this cocktail.
 */
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
    var nrOfOwnedIngredients: Int,
)

/**
 * Data class representing a cross-reference entity between Cocktail and Ingredient in the Room database.
 *
 * @property cocktailId The unique identifier for the cocktail.
 * @property ingredientName The name of the ingredient associated with the cocktail.
 */
@Entity(primaryKeys = ["cocktailId", "ingredientName"])
data class CrossRef(
    val cocktailId: Int,
    val ingredientName: String,
)
