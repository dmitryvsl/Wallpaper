package com.example.wallpaper.core.exception

import androidx.annotation.StringRes
import com.example.wallpaper.R
import java.net.ConnectException
import java.net.SocketTimeoutException

object ExceptionMapper {
    @StringRes
    fun getDescription(e: Throwable): Int = when(e){
        is TooManyRequestException -> R.string.serverBusyNow
        is ConnectException -> R.string.connectionError
        is SocketTimeoutException -> R.string.connectionError
        is NoNetworkException -> R.string.noNetwork
        else -> R.string.errorOccured
    }
}