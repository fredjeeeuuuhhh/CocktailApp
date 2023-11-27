package com.example.cocktailapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.cocktailapp.model.Cocktail

/**
 * Internal model used to represent a cocktail stored locally in a Room database. This is used inside
 * the data layer only.
 */
@Entity(tableName="cocktails")
data class dbCocktail(
    @PrimaryKey(autoGenerate=false)
    val cocktailId: Int =0,
    val title: String = "",
    val category: String?,
    @ColumnInfo(name = "alcohol_filter")
    val alcoholFilter: String?,
    @ColumnInfo(name = "type_of_glass")
    val typeOfGlass: String?,
    val instructions: String?,
    val image: String = "",
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean=false,
)

@Entity(tableName="measurements")
data class dbMeasurement(
    @PrimaryKey(autoGenerate=true)
    val id: Int =0,
    val measurementOwnerId: Int = 0,
    val measurement: String = "",
)

data class CocktailWithMeasurements(
    @Embedded val cocktail: dbCocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "measurementOwnerId"
    )
    val measurements: List<dbMeasurement>,
)

@Entity(primaryKeys = ["cocktailId", "ingredientId"])
data class CocktailIngredientCrossRef(
    val cocktailId: Int,
    val ingredientId: Int,
)

data class CocktailWithIngredients(
    @Embedded val cocktail: dbCocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "ingredientId",
        associateBy = Junction(CocktailIngredientCrossRef::class)
    )
    val ingredients: List<dbIngredient>,
)

fun dbMeasurement.asDomainMeasurement():String {
    return this.measurement
}

fun String.asDbMeasurement(cocktailId:Int): dbMeasurement {
    return dbMeasurement(
        measurement = this,
        measurementOwnerId = cocktailId,
    )
}

fun List<dbMeasurement>.asDomainMeasurements(): List<String> {
    val list = {
        this.map {
            it.measurement
        }
    }
    return list.invoke()
}

fun dbCocktail.asDomainCocktail(): Cocktail {
    return Cocktail(
        id = this.cocktailId,
        title = this.title,
        category = this.category,
        alcoholFilter = this.alcoholFilter,
        typeOfGlass = this.typeOfGlass,
        instructions = this.instructions,
        image = this.image,
        ingredients = null,
        ingredientNames = null,//not sure yet, in mapping adding it to cocktail domain obj?
        measurements = null,
        isFavorite = this.isFavorite,
    )
}
fun List<dbCocktail>.asDomainCocktails(): List<Cocktail> {
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
            ingredientNames = null, //not sure yet, in mapping adding it to cocktail domain obj?
            measurements = null,
            isFavorite = it.isFavorite,
        )
    }
    return list
}

fun Cocktail.asDbCocktail(): dbCocktail {
    return dbCocktail(
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
