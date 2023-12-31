package com.example.cocktailapp.fake

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.ApiCocktail
import com.example.cocktailapp.network.ApiIngredient

object FakeDataSource {
    private val cocktail1 = Cocktail(
        1,
        "Bacardi cola",
        "Cocktail",
        "Alcohol",
        "Cola glass",
        "1. Poor bacardi then cola",
        "www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg",
        listOf("Bacardi", "Cola"),
        listOf("1 oz", "1 can"),
        isFavorite = true,
    )
    private val cocktail2 = Cocktail(
        1,
        "Vodka Redbull",
        "Cocktail",
        "Alcohol",
        "cup",
        "1. Poor vodka then redbull",
        "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg",
        listOf("Vodka", "Redbull"),
        listOf("1 oz", "1 can"),
    )
    private val ingredient1 = Ingredient(
        "Cola",
        "Coca cola",
        "Soda",
        false,
        "0",
        "https://www.thecocktaildb.com/images/ingredients/cola-Small.png",
        isOwned = true,
    )
    private val ingredient2 = Ingredient(
        "Bacardi",
        "Strong good alcohol",
        "Alcohol",
        true,
        "40",
        "https://www.thecocktaildb.com/images/ingredients/bacardi-Small.png",
    )
    val cocktail = listOf(
        ApiCocktail(
            cocktail1.id.toString(),
            cocktail1.title,
            cocktail1.category!!,
            cocktail1.alcoholFilter!!,
            cocktail1.typeOfGlass!!,
            cocktail1.instructions!!,
            cocktail1.image,
            cocktail1.ingredientNames!![0],
            cocktail1.ingredientNames!![1],
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            strMeasure1 = cocktail1.measurements!![0],
            strMeasure2 = cocktail1.measurements!![1],
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        ),
        ApiCocktail(
            cocktail2.id.toString(),
            cocktail2.title,
            cocktail2.category!!,
            cocktail2.alcoholFilter!!,
            cocktail2.typeOfGlass!!,
            cocktail2.instructions!!,
            cocktail2.image,
            cocktail2.ingredientNames!![0],
            cocktail2.ingredientNames!![1],
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            strMeasure1 = cocktail2.measurements!![0],
            strMeasure2 = cocktail2.measurements!![1],
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        ),
    )
    val ingredients = listOf(
        ApiIngredient(
            "0",
            ingredient1.name,
            ingredient1.description!!,
            ingredient1.type!!,
            if (ingredient1.containsAlcohol!!) "Yes" else "No",
            ingredient1.alcoholPercentage!!,
        ),
        ApiIngredient(
            "1",
            ingredient2.name,
            ingredient2.description!!,
            ingredient2.type!!,
            if (ingredient2.containsAlcohol!!) "Yes" else "No",
            ingredient2.alcoholPercentage!!,
        ),
    )
}
