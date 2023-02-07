package com.example.wallpaper.feature.image_list.domain.model

enum class HttpStatusCode(val code: Int){

    //2.x.x
    Ok(200),

    //4.x.x
    TooManyRequests(429)
}