package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.cocktailapp.ui.cocktails.components.CocktailHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetail(
    cocktailDetailViewModel: CocktailDetailViewModel,
    onBack: ()-> Unit,
    name: String?,
){
    val state by cocktailDetailViewModel.uiState.collectAsState()
    cocktailDetailViewModel.setCurrentCocktail(name!!)

    Scaffold(
        topBar = {
            CocktailHeader(onBack=onBack, title = name)
        }
    ) {innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){

        }

    }
}