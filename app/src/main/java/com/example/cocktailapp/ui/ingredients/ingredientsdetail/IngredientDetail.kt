package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cocktailapp.ui.ingredients.components.IngredientHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetail(
    onBack: ()-> Unit,
    ingredientDetailViewModel: IngredientDetailViewModel,
    name: String?,
){
    Scaffold(
        topBar = {
            IngredientHeader(
                onBack=onBack,title=name
            )
        }
    ) {innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){

        }

    }
}