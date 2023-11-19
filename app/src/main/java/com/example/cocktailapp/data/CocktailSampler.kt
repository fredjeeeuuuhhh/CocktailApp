package com.example.cocktailapp.data

import com.example.cocktailapp.model.Cocktail

object CocktailSampler {
    val cocktails = mutableListOf<Cocktail>(
        Cocktail(
            idDrink = "15300",
            strDrink = "3-Mile Long Island Iced Tea",
            strCategory = "Ordinary Drink",
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg",
            ingredients = listOf("Gin", "Light rum", "Tequila", "Triple sec", "Vodka", "Coca-Cola", "Sweet and sour", "Bitters", "Lemon"),
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
        ),
        Cocktail(
            idDrink = "15300",
            strDrink = "3-Mile Long Island Iced Tea",
            strCategory = "Ordinary Drink",
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg",
            ingredients = listOf("Gin", "Light rum", "Tequila", "Triple sec", "Vodka", "Coca-Cola", "Sweet and sour", "Bitters", "Lemon"),
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
        ),
        Cocktail(
            idDrink = "15300",
            strDrink = "3-Mile Long Island Iced Tea",
            strCategory = "Ordinary Drink",
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg",
            ingredients = listOf("Gin", "Light rum", "Tequila", "Triple sec", "Vodka", "Coca-Cola", "Sweet and sour", "Bitters", "Lemon"),
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
        ),
        Cocktail(
            idDrink = "15300",
            strDrink = "3-Mile Long Island Iced Tea",
            strCategory = "Ordinary Drink",
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg",
            ingredients = listOf("Gin", "Light rum", "Tequila", "Triple sec", "Vodka", "Coca-Cola", "Sweet and sour", "Bitters", "Lemon"),
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
        ),
        Cocktail(
            idDrink = "15300",
            strDrink = "3-Mile Long Island Iced Tea",
            strCategory = "Ordinary Drink",
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg",
            ingredients = listOf("Gin", "Light rum", "Tequila", "Triple sec", "Vodka", "Coca-Cola", "Sweet and sour", "Bitters", "Lemon"),
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
        ),
    )

    val getAll: () -> MutableList<Cocktail> = {
        cocktails
    }
}
