package com.example.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    viewState: MainViewModel.RecipeState,
    navigateToDetail: (category: Category) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        // 8. GET THE STATE DATA FROM view Model
        when {
            viewState.loading -> CircularProgressIndicator(modifier.align(Alignment.Center))
            viewState.error != null -> Text(text = "OOPS SOMETHING WENT WRONG!")
            else -> {
                CategoriesList(categories = viewState.list, navigateToDetail)
            }
        }
    }
}

// 9. CREATE A COMPOSABLE WITH CATEGORIES LIST RESPONSE
@Composable
fun CategoriesList(categories: List<Category>, navigateToDetail: (category: Category) -> Unit) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(categories) { category ->
            CategoryItem(category = category, navigateToDetail)
        }
    }
}

// 10. ITEM COMPOSABLE CATEGORIES WITH CATEGORY MODEL
@Composable
fun CategoryItem(category: Category, navigateToDetail: (category: Category) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .fillMaxSize()
            .clickable { navigateToDetail(category) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = category.strCategoryThumb),
            contentDescription = null
        )
        Text(
            text = category.strCategory,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
