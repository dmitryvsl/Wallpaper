package com.example.wallpaper.core.data

import androidx.compose.ui.graphics.painter.Painter

object SharedPainter {

    private var painter: Painter? = null
    private var highQualityImageUrl: String? = null

    fun savePainter(painter: Painter?) {
        this.painter = painter
    }

    fun getPainter(): Painter?{
        val cachedPainter = painter
        painter = null
        return cachedPainter
    }

    fun saveUrl(url: String){
        highQualityImageUrl = url
    }

    fun getUrl(): String? {
        val cachedUrl = highQualityImageUrl
        highQualityImageUrl = null
        return cachedUrl
    }
}