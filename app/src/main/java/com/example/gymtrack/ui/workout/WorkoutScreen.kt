package com.example.gymtrack.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtrack.ui.home.backgroundColor
import com.example.gymtrack.ui.home.greenColor
import com.example.gymtrack.ui.home.surfaceColor
import com.example.gymtrack.ui.home.textPrimary
import com.example.gymtrack.ui.home.textSecondary

@Composable
fun WorkoutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Active Workout",
                    color = textPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "⏱ 00:00",
                    color = textSecondary,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Finish", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "+ Add Exercise", color = textPrimary, fontSize = 16.sp)
        }
    }
}