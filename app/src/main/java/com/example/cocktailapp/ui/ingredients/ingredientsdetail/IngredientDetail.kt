package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun IngredientDetail(
    onBack: ()-> Unit,
    ingredientDetailViewModel: IngredientDetailViewModel = hiltViewModel(),
){
    Column(){
        Text("Ingredient detail")
    }
}