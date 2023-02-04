package com.example.wallpaper.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.wallpaper.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class StartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                )
                onDispose {}
            }

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    CircularProgressIndicator()
                }
            }
        }

        Thread {
            Thread.sleep(1000L)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.start()

    }
}