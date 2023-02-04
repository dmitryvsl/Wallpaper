package com.example.wallpaper.feature.category_detail.presentation

import com.example.wallpaper.core.base.BaseViewModel
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.feature.category_detail.domain.model.Image
import com.example.wallpaper.feature.category_detail.domain.repository.ImageListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageListRepository
) : BaseViewModel<List<Image>>() {

    private var page = 1
    private var categoryType: CategoryType? = null

    override val data: MutableStateFlow<MutableList<Image>> = MutableStateFlow(mutableListOf())
    override val loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override val error: MutableStateFlow<Throwable?> = MutableStateFlow(null)

    private var job: Job? = null

    override fun setLoading(loading: Boolean) {
        this.loading.value = loading
    }

    override fun setError(t: Throwable?) {
        this.error.value = t
    }


    fun fetchImages(categoryType: CategoryType) {
        this.categoryType = categoryType
        fetchNextPage()
    }

    fun fetchNextPage() {
        if (categoryType != null && job?.isCompleted != false) {
            job = makeCall(
                call = {
                    repository.fetchImages(categoryType!!, page)
                },
                onSuccess = { images ->
                    this.data.value.addAll(images)
                    page++
                },
                onError = { t -> setError(t) }
            )
        }
    }
}