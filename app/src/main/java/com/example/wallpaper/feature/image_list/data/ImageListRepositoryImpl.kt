package com.example.wallpaper.feature.image_list.data

import com.example.wallpaper.core.base.BaseRepository
import com.example.wallpaper.core.data.ConnectivityManagerNetworkMonitor
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.image_list.data.datasource.ApiService
import com.example.wallpaper.feature.image_list.data.datasource.model.asDomain
import com.example.wallpaper.feature.image_list.data.datasource.model.NetworkResponse
import com.example.wallpaper.feature.image_list.domain.model.Image
import com.example.wallpaper.feature.image_list.domain.repository.ImageListRepository
import javax.inject.Inject

class ImageListRepositoryImpl @Inject constructor(
    private val apiService: ApiService, networkMonitor: ConnectivityManagerNetworkMonitor,
) : ImageListRepository, BaseRepository<NetworkResponse>(networkMonitor) {

    override suspend fun fetchImages(
        categoryType: CategoryType,
        page: Int
    ): DataState<List<Image>> {

        val response = makeCall {
            apiService.fetchImages(categoryType.name, page)
        }

        if (response is DataState.Success)
            return DataState.Success(response.data.hits.map { hit -> hit.asDomain() })

        return DataState.Error((response as DataState.Error).e)
    }

}
