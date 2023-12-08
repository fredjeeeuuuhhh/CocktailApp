package com.example.cocktailapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient

/**
 * Internal model used to represent a cocktail stored locally in a Room database. This is used inside
 * the data layer only.
 */
@Entity(tableName="cocktails")
data class DbCocktail(
    @PrimaryKey(autoGenerate=false)
    val cocktailId: Int,
    val title: String,
    val category: String?,
    @ColumnInfo(name = "alcohol_filter")
    val alcoholFilter: String?,
    @ColumnInfo(name = "type_of_glass")
    val typeOfGlass: String?,
    val instructions: String?,
    val image: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
)

@Entity(tableName="measurements")
data class DbMeasurement(
    @PrimaryKey(autoGenerate=true)
    val id: Int =0,
    val measurementOwnerId: Int = 0,
    val measurement: String = "",
)
@Entity(tableName="ingredient_names")
data class DbIngredientName(
    @PrimaryKey(autoGenerate=true)
    val id: Int =0,
    val ingredientNameOwnerId: Int = 0,
    val name: String = "",
)

data class CocktailWithMeasurements(
    @Embedded val cocktail: DbCocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "measurementOwnerId"
    )
    val measurements: List<DbMeasurement>,
)
data class CocktailWithIngredientNames(
    @Embedded val cocktail: DbCocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "ingredientNameOwnerId"
    )
    val ingredientNames: List<DbIngredientName>,
)

@Entity(primaryKeys = ["cocktailId", "name"])
data class CocktailIngredientCrossRef(
    val cocktailId: Int,
    val name: String,
)

data class CocktailWithIngredients(
    @Embedded val cocktail: DbCocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "ingredientId",
        associateBy = Junction(CocktailIngredientCrossRef::class)
    )
    val ingredients: List<DbIngredient>,
)
data class IngredientWithCocktails(
    @Embedded val cocktail: DbIngredient,
    @Relation(
        parentColumn = "ingredientId",
        entityColumn = "cocktailId",
        associateBy = Junction(CocktailIngredientCrossRef::class)
    )
    val ingredients: List<DbCocktail>,
)
fun List<DbIngredientName>.asDomainIngredientsNamesOnly():List<Ingredient>{
    return this.map { Ingredient(
        name = it.name,
        thumbnail = "${"https://"}www.thecocktaildb.com/images/ingredients/${it.name}-Small.png",
    )}
}
fun DbMeasurement.asDomainMeasurement():String {
    return this.measurement
}
fun DbIngredientName.asDomainIngredientName():String {
    return this.name
}
fun List<String>.asDbMeasurements(): List<DbMeasurement>{
    return this.map {
        it.asDbMeasurement()
    }
}
fun List<String>.asDbIngredientNames(): List<DbIngredientName>{
    return this.map {
        it.asDbIngredientName()
    }
}
fun String.asDbMeasurement(): DbMeasurement {
    return DbMeasurement(
        measurement = this,
        measurementOwnerId =0,
    )
}
fun String.asDbIngredientName(): DbIngredientName {
    return DbIngredientName(
        name = this,
        ingredientNameOwnerId =0,
    )
}

fun List<DbMeasurement>.asDomainMeasurements(): List<String> {
    val list = {
        this.map {
            it.measurement
        }
    }
    return list.invoke()
}
fun List<DbIngredientName>.asDomainIngredientNames(): List<String> {
    val list = {
        this.map {
            it.name
        }
    }
    return list.invoke()
}

fun DbCocktail.asDomainCocktail(measurements: List<String>,ingredientNames: List<String>): Cocktail {
    return Cocktail(
        id = this.cocktailId,
        title = this.title,
        category = this.category,
        alcoholFilter = this.alcoholFilter,
        typeOfGlass = this.typeOfGlass,
        instructions = this.instructions,
        image = this.image,
        ingredients = null,
        ingredientNames = ingredientNames,
        measurements = measurements,
        isFavorite = this.isFavorite,
    )
}
fun List<DbCocktail>.asDomainCocktails(): List<Cocktail> {
    val list = this.map {
        Cocktail(
            id = it.cocktailId,
            title = it.title,
            category = it.category,
            alcoholFilter = it.alcoholFilter,
            typeOfGlass = it.typeOfGlass,
            instructions = it.instructions,
            image = it.image,
            ingredients = null,
            ingredientNames = null,
            measurements = null,
            isFavorite = it.isFavorite,
        )
    }
    return list
}

fun Cocktail.asDbCocktail(): DbCocktail {
    return DbCocktail(
        cocktailId = this.id,
        title = this.title,
        category = this.category,
        alcoholFilter = this.alcoholFilter,
        typeOfGlass = this.typeOfGlass,
        instructions = this.instructions,
        image = this.image,
        isFavorite = this.isFavorite!!,
    )
}
