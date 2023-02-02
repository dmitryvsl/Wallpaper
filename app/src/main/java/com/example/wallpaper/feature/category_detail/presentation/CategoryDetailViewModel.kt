package com.example.wallpaper.feature.category_detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.domain.model.Image
import com.example.wallpaper.feature.category_detail.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _images: MutableStateFlow<DataState<List<Image>>> = MutableStateFlow(DataState.Loading())
    val images: StateFlow<DataState<List<Image>>> = _images

    fun fetchImages(categoryType: CategoryType){
        viewModelScope.launch (Dispatchers.IO) {
            _images.value = DataState.Loading()
            delay(2000L)
            _images.value = repository.fetchImages(categoryType)
        }
    }
}