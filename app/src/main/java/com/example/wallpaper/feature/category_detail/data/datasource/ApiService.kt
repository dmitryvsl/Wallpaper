package com.example.wallpaper.feature.category_detail.data.datasource

import com.example.wallpaper.feature.category_detail.data.datasource.model.NetworkResponse
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