package com.example.appvoltaje.adapters.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appvoltaje.R
import com.example.appvoltaje.adapters.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

private val SplashBackground = Color(0xFF0C3D1E)
private val SplashAccent = Color(0xFFB6FF6B)

@Composable
fun SplashScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigateToScanner: () -> Unit
) {

    val alphaAnim = remember { Animatable(0f) }


    val walkOffset = remember { Animatable(0f) }


    val walkRotation = remember { Animatable(0f) }

    val slideX = remember { Animatable(-300f) }

    val textAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {

        slideX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 700, easing = EaseOutCubic)
        )

        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )

        repeat(3) {
            walkOffset.animateTo(
                targetValue = -8f,
                animationSpec = tween(durationMillis = 180, easing = LinearEasing)
            )
            walkRotation.animateTo(
                targetValue = 4f,
                animationSpec = tween(durationMillis = 180, easing = LinearEasing)
            )
            walkOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 180, easing = LinearEasing)
            )
            walkRotation.animateTo(
                targetValue = -4f,
                animationSpec = tween(durationMillis = 180, easing = LinearEasing)
            )
        }
        walkRotation.animateTo(0f, animationSpec = tween(100))

        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400)
        )

        delay(800)

        val currentUser = viewModel.getCurrentUser()
        if (currentUser != null) {
            onNavigateToScanner()
        } else {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_playground),
                contentDescription = "Playground Logo",
                modifier = Modifier
                    .size(180.dp)
                    .alpha(alphaAnim.value)
                    .graphicsLayer {
                        translationX = slideX.value
                        translationY = walkOffset.value
                        rotationZ = walkRotation.value
                        transformOrigin = TransformOrigin(0.5f, 0.8f)
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Playground",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .alpha(alphaAnim.value)
                    .graphicsLayer { translationX = slideX.value }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(3.dp)
                    .alpha(alphaAnim.value)
                    .background(SplashAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtítulo
            Text(
                text = "SMART MONITORING",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SplashAccent,
                letterSpacing = 3.sp,
                modifier = Modifier.alpha(textAlpha.value)
            )
        }
    }
}