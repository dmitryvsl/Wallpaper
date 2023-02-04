package com.example.wallpaper.core.base

import com.example.wallpaper.core.data.ConnectivityManagerNetworkMonitor
import com.example.wallpaper.core.exception.NoNetworkException
import com.example.wallpaper.core.exception.TooManyRequestException
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.domain.model.HttpStatusCode
import kotlinx.coroutines.flow.first
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class BaseRepository<T>(
    private val networkMonitor: ConnectivityManagerNetworkMonitor
) {

    suspend fun makeCall(
        call: suspend () -> Response<T>,
    ): DataState<T> {
        if (!isOnline()) return DataState.Error(NoNetworkException())

        val response = try {
            call.invoke()
        } catch (e: ConnectException) {
            return DataState.Error(e)
        } catch (e: SocketTimeoutException) {
            return DataState.Error(e)
        } catch (e: Exception) {
            return DataState.Error(UnknownError())
        }
        return when (response.code()) {
            HttpStatusCode.Ok.code ->
                return DataState.Success(response.body()!!)

            HttpStatusCode.TooManyRequests.code -> DataState.Error(TooManyRequestException())
            else -> DataState.Error(UnknownError())
        }
    }

    private suspend fun isOnline(): Boolean = networkMonitor.isOnline.first()
}