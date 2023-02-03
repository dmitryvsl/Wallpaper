package com.example.wallpaper.feature.category_detail.domain.model

enum class HttpStatusCode(val code: Int){

    //2.x.x
    Ok(200),

    //4.x.x
    TooManyRequests(429)
}