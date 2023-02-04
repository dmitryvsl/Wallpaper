package com.example.wallpaper.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.core.model.DataState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : Any> : ViewModel() {
    abstract val data: StateFlow<T>
    abstract val loading: StateFlow<Boolean>
    abstract val error: StateFlow<Throwable?>

    abstract fun setLoading(loading: Boolean)
    abstract fun setError(t: Throwable?)

    fun makeCall(
        call: suspend () -> DataState<T>,
        onError: (Throwable) -> Unit,
        onSuccess: (T) -> Unit
    ): Job = viewModelScope.launch {
        setError(null)
        setLoading(true)

        val response = call.invoke()

        setLoading(false)

        if (response is DataState.Error) {
            onError(response.e)
            return@launch
        }

        onSuccess((response as DataState.Success).data)

    }
}