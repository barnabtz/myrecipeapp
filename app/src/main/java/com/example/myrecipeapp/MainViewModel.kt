package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // 2. WE STORE THE RECIPE STATE USING mutableStateOf FIR THE VIEW MODEL
    private val _categoriesState = mutableStateOf(RecipeState())

    // 3. WE READY THE STATE FOR ACCESS & COMSUMPTION FOR THE COMPOSE VIEW
    val categorieState: State<RecipeState> = _categoriesState

    // 5. YOU INITIALIZE THE FETCHCATEGORIES
    init {
        fetchCategories()
    }

    // 4. FETCH THE DATA LIST FROM APISERVICE USING viewModelScope.launch
    // NB: we using the viewmodelscope due the suspend key found on the API service
    // NB: Remember the data will be get using the RECIPESTATE. Take note
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeApi.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = response.categories,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error occurred while fetching ${e.message}"
                )
            }
        }
    }


    // 1. CREATE A RECIPE STATE FOR THE APP
    data class RecipeState(
        val loading: Boolean = true, // differentiate if the data is loading
        val list: List<Category> = listOf(), // data available and ready
        val error: String? = null // in case of error or failure for the state
    )

}