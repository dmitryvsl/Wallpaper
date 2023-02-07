package com.example.wallpaper.feature.image_list.data.datasource

import com.example.wallpaper.feature.image_list.data.datasource.model.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun fetchImages(
        @Query("q") category: String,
        @Query("page") page: Int
    ): Response<NetworkResponse>

}