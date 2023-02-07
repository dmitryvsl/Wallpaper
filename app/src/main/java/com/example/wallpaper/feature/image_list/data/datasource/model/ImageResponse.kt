package com.example.wallpaper.feature.image_list.data.datasource.model

import com.example.wallpaper.feature.image_list.domain.model.Image
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hits>,
)

@Serializable
data class Hits(
    var id: Int,
    var pageURL: String,
    var type: String,
    var tags: String,
    var previewURL: String,
    var previewWidth: Int,
    var previewHeight: Int,
    var webformatURL: String,
    var webformatWidth: Int,
    var webformatHeight: Int,
    var largeImageURL: String,
    var imageWidth: Int,
    var imageHeight: Int,
    var imageSize: Int,
    var views: Int,
    var downloads: Int,
    var collections: Int,
    var likes: Int,
    var comments: Int,
    var user_id: Int,
    var user: String,
    var userImageURL: String
)

fun Hits.asDomain() = Image(
    id = id,
    url = webformatURL,
    fullImageUrl = largeImageURL
)
