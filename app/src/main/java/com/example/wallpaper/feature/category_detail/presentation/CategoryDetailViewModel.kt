package com.example.wallpaper.feature.category_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.domain.model.Image
import com.example.wallpaper.feature.category_detail.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private var page = 1
    private var categoryType: CategoryType? = null

    private var _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private var _error: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val error: StateFlow<Throwable?> = _error

    private val _images: MutableStateFlow<MutableList<Image>> = MutableStateFlow(mutableListOf())
    val images: StateFlow<List<Image>> = _images
    private var job: Job? = null

    fun fetchImages(categoryType: CategoryType) {
        this.categoryType = categoryType
        fetchNextPage()
    }

    fun fetchNextPage(){
        if (categoryType != null && job?.isCompleted != false)
            job = viewModelScope.launch(Dispatchers.IO) {
                _error.value = null
                _loading.value = true
                val response  = repository.fetchImages(categoryType!!, page)
                _loading.value = false
                if (response is DataState.Error){
                    _error.value = response.e
                    return@launch
                }
                _images.value.addAll((response as DataState.Success).data)
                page++
            }
    }
}