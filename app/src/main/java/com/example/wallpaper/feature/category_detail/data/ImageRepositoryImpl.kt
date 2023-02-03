package com.example.wallpaper.feature.category_detail.data

import com.example.wallpaper.core.data.ConnectivityManagerNetworkMonitor
import com.example.wallpaper.core.exception.NoNetworkException
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.data.datasource.ApiService
import com.example.wallpaper.feature.category_detail.data.datasource.model.asDomain
import com.example.wallpaper.core.exception.TooManyRequestException
import com.example.wallpaper.feature.category_detail.domain.model.HttpStatusCode
import com.example.wallpaper.feature.category_detail.domain.model.Image
import com.example.wallpaper.feature.category_detail.domain.repository.ImageRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkMonitor: ConnectivityManagerNetworkMonitor
) : ImageRepository {

    override suspend fun fetchImages(
        categoryType: CategoryType,
        page: Int
    ): DataState<List<Image>> {
        if (!isOnline()) return DataState.Error(NoNetworkException())

        val response = try {
            apiService.fetchImages(categoryType.name, page)
        } catch (e: ConnectException) {
            return DataState.Error(e)
        } catch (e: SocketTimeoutException) {
            return DataState.Error(e)
        } catch (e: Exception) {
            return DataState.Error(UnknownError())
        }
        return when (response.code()) {
            HttpStatusCode.Ok.code -> {
                val images = response.body()!!.hits
                return DataState.Success(images.map { imageResponse -> imageResponse.asDomain() })
            }

            HttpStatusCode.TooManyRequests.code -> DataState.Error(TooManyRequestException())
            else -> DataState.Error(UnknownError())
        }
    }

    private suspend fun isOnline(): Boolean = networkMonitor.isOnline.first()
}
