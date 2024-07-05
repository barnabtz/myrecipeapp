package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navHostController: NavHostController) {
    var viewModel: MainViewModel = viewModel()
    val viewState by viewModel.categorieState

    NavHost(navController = navHostController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewState, navigateToDetail = {
                navHostController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                navHostController.navigate(Screen.DetailScreen.route)
            })
        }

        composable(route = Screen.DetailScreen.route) {
            val category =
                navHostController.currentBackStackEntry?.savedStateHandle?.get<Category>("cat")
                    ?: Category("", "", "", "")
            CategoryDetailScreen(category = category)
        }
    }
}