package com.example.ngkafisha.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ngkafisha.R
import com.example.ngkafisha.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SplashScreen(controlNav: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {
    val rotation = remember { Animatable(0f) }
    val isLoading by viewModel.isLoading.collectAsState()
    val shouldNavigateToLogin by viewModel.shouldNavigateToLogin.collectAsState()
    val shouldNavigateToHome by viewModel.shouldNavigateToHome.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkSession()
    }

    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = repeatable(
                iterations = 2,
                animation = tween(durationMillis = 878, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    LaunchedEffect(shouldNavigateToLogin, shouldNavigateToHome) {
        if (!isLoading) {
            delay(500L)
            when {
                shouldNavigateToHome -> {
                    controlNav.navigate("listEventScreen") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
                shouldNavigateToLogin -> {
                    controlNav.navigate("signIn") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }
    }


    BoxWithConstraints {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp))
        {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            )
            {
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer {
                            rotationZ = rotation.value
                        })

                Spacer(modifier = Modifier.padding(16.dp))

                Text(
                    text = "NGKAfisha",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )
            }

        }
    }
}