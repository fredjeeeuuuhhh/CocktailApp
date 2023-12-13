package com.example.cocktailapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cocktailapp.model.Ingredient

@Entity(tableName = "ingredients")
data class DbIngredient(
    @PrimaryKey(autoGenerate = false)
    val ingredientId: Int = 0,
    val name: String,
    val description: String?,
    val type: String?,
    @ColumnInfo(name = "contains_alcohol")
    val containsAlcohol: Boolean?,
    @ColumnInfo(name = "alcohol_percentage")
    val alcoholPercentage: String?,
    val thumbnail: String,
    @ColumnInfo(name = "is_owned")
    val isOwned: Boolean = false,
)

fun DbIngredient?.asDomainIngredient(): Ingredient {
    return Ingredient(
        id = this?.ingredientId,
        name = this?.name ?: "test",
        description = this?.description,
        type = this?.type,
        containsAlcohol = this?.containsAlcohol,
        alcoholPercentage = this?.alcoholPercentage,
        thumbnail = this?.thumbnail ?: "test",
        isOwned = this?.isOwned,
    )
}

fun Ingredient.asDbIngredient(): DbIngredient {
    return DbIngredient(
        ingredientId = this.id!!,
        name = this.name,
        description = this.description,
        type = this.type,
        containsAlcohol = this.containsAlcohol,
        alcoholPercentage = this.alcoholPercentage,
        thumbnail = this.thumbnail,
        isOwned = this.isOwned?:false,
    )
}

fun List<DbIngredient>.asDomainIngredients(): List<Ingredient> {
    val ingredientList = this.map {
       it.asDomainIngredient()
    }
    return ingredientList
}