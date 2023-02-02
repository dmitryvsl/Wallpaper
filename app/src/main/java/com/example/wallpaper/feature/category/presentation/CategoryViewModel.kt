package com.example.wallpaper.feature.category.presentation

import androidx.lifecycle.ViewModel
import com.example.wallpaper.R
import com.example.wallpaper.core.model.CategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {

    private val category = listOf(
        Category(
            type = CategoryType.Animals,
            title = R.string.animals,
            image = R.drawable.animals
        ),
        Category(
            type = CategoryType.Music,
            title = R.string.music,
            image = R.drawable.music
        ),
        Category(
            type = CategoryType.Nature,
            title = R.string.nature,
            image = R.drawable.nature
        ),
        Category(
            type = CategoryType.Fashion,
            title = R.string.fashion,
            image = R.drawable.fashion
        ),
        Category(
            type = CategoryType.Sports,
            title = R.string.sports,
            image = R.drawable.sports
        ),
        Category(
            type = CategoryType.People,
            title = R.string.people,
            image = R.drawable.people
        ),
    )

    val categories: StateFlow<List<Category>> = MutableStateFlow(category)
}