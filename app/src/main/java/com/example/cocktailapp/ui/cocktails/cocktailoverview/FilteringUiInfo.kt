package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoDrinks
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cocktailapp.R

data class FavoritesFilteringUiInfo(
    val currentFiltereingLabel: Int = R.string.chip_label_favorites,
    val noTasksLabel: Int = R.string.no_cocktails_all,
    val noTaskIconRes: ImageVector = Icons.Filled.NoDrinks,
)
data class CategoryFilteringUiInfo(
    val currentFiltereingLabel: Int = R.string.chip_label_favorites,
    val noTasksLabel: Int = R.string.no_cocktails_all,
    val noTaskIconRes: ImageVector = Icons.Filled.NoDrinks,
)
data class GlassFilteringUiInfo(
    val currentFiltereingLabel: Int = R.string.chip_label_favorites,
    val noTasksLabel: Int = R.string.no_cocktails_all,
    val noTaskIconRes: ImageVector = Icons.Filled.NoDrinks,
)
data class AlcoholicFilteringUiInfo(
    val currentFiltereingLabel: Int = R.string.chip_label_favorites,
    val noTasksLabel: Int = R.string.no_cocktails_all,
    val noTaskIconRes: ImageVector = Icons.Filled.NoDrinks,
)
