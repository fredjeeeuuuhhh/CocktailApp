package com.example.cocktailapp.data

import com.example.cocktailapp.model.Cocktail

object CocktailSampler {
    val cocktails = mutableListOf(
        Cocktail(
            id = 15300,
            title = "3-Mile Long Island Iced Tea",
            category = "Ordinary Drink",
            alcoholFilter = "Alcoholic",
            typeOfGlass = "Collins Glass",
            instructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            image = "https://www.thecocktaildb.com/images/media/drink/rrtssw1472668972.jpg",
            ingredients = IngredientSampler.ingredients,
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
            isFavorite = true
        ),
        Cocktail(
            id = 15300,
            title = "Cocktail 2",
            category = "Ordinary Drink",
            alcoholFilter = "Alcoholic",
            typeOfGlass = "Collins Glass",
            instructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            image = "https://www.thecocktaildb.com/images/media/drink/3k9qic1493068931.jpg",
            ingredients = IngredientSampler.ingredients,
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
            isFavorite = false
        ),
        Cocktail(
            id = 15300,
            title = "Cocktail 3",
            category = "Ordinary Drink",
            alcoholFilter = "Alcoholic",
            typeOfGlass = "Collins Glass",
            instructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            image = "https://www.thecocktaildb.com/images/media/drink/tqpvqp1472668328.jpg",
            ingredients = IngredientSampler.ingredients,
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
            isFavorite = false
        ),
        Cocktail(
            id = 15300,
            title = "Cocktail 4",
            category = "Ordinary Drink",
            alcoholFilter = "Alcoholic",
            typeOfGlass = "Collins Glass",
            instructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            image = "https://www.thecocktaildb.com/images/media/drink/l3cd7f1504818306.jpg",
            ingredients = IngredientSampler.ingredients,
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
            isFavorite = false
        ),
        Cocktail(
            id = 15300,
            title = "Cocktail 5",
            category = "Ordinary Drink",
            alcoholFilter = "Alcoholic",
            typeOfGlass = "Collins Glass",
            instructions = "Fill 14oz glass with ice and alcohol. Fill 2/3 glass with cola and remainder with sweet & sour. Top with dash of bitters and lemon wedge.",
            image = "https://www.thecocktaildb.com/images/media/drink/xuxpxt1479209317.jpg",
            ingredients = IngredientSampler.ingredients,
            measurements = listOf("1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1/2 oz", "1-2 dash ", "1 wedge ", "Garnish with"),
            isFavorite = false
        ),
    )
}
