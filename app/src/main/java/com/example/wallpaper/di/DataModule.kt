package com.example.wallpaper.di

import com.example.wallpaper.BuildConfig
import com.example.wallpaper.core.data.ThemeConfigurator
import com.example.wallpaper.core.data.ThemeMonitor
import com.example.wallpaper.feature.image_list.data.ImageListRepositoryImpl
import com.example.wallpaper.feature.image_list.data.datasource.ApiService
import com.example.wallpaper.feature.image_list.domain.repository.ImageListRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsImageListRepository(repository: ImageListRepositoryImpl): ImageListRepository

    @Binds
    fun bindsThemeMonitor(themeMonitor: ThemeConfigurator): ThemeMonitor
    companion object{

        @Provides
        @Singleton
        fun provideApiService(): ApiService = createRetrofit().create(ApiService::class.java)

        @OptIn(ExperimentalSerializationApi::class)
        private fun createRetrofit() = Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(getOkHttpClient())
            .build()

        private fun getLoggingInterceptor() =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

        private fun getApiKeyInterceptor() =
            Interceptor {chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", BuildConfig.API_KEY)
                    .build()
                val requestBuilder = original.newBuilder().url(url).build()
                return@Interceptor chain.proceed(requestBuilder)
            }
        private fun getOkHttpClient() = OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor(getApiKeyInterceptor())
            .build()
    }
}