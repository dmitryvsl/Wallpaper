package com.example.wallpaper.presentation.category

import androidx.lifecycle.ViewModel
import com.example.wallpaper.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {

    private val category = listOf(
        Category(
            title = R.string.animals,
            image = R.drawable.animals
        ),
        Category(
            title = R.string.music,
            image = R.drawable.music
        ),
        Category(
            title = R.string.nature,
            image = R.drawable.nature
        ),
        Category(
            title = R.string.fashion,
            image = R.drawable.fashion
        ),
        Category(
            title = R.string.sports,
            image = R.drawable.sports
        ),
        Category(
            title = R.string.people,
            image = R.drawable.people
        ),
    ).sortedBy { category -> category.title }

    val categories: StateFlow<List<Category>> = MutableStateFlow(category)
}