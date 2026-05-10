package com.example.gymtrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val backgroundColor = Color(0xFF0A0A0A)
val surfaceColor = Color(0xFF1A1A1A)
val greenColor = Color(0xFF4ADE80)
val textPrimary = Color(0xFFFFFFFF)
val textSecondary = Color(0xFF9CA3AF)

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "GymTrack",
            color = textPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Ready to crush it?",
            color = textSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = greenColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "▶  Start Empty Workout",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Quick Start",
            color = textPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}