package com.example.gymtrack.ui.history

import androidx.compose.ui.res.stringResource
import com.example.gymtrack.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtrack.data.db.WorkoutEntity
import com.example.gymtrack.ui.home.backgroundColor
import com.example.gymtrack.ui.home.greenColor
import com.example.gymtrack.ui.home.surfaceColor
import com.example.gymtrack.ui.home.textPrimary
import com.example.gymtrack.ui.home.textSecondary
import com.example.gymtrack.ui.viewmodel.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.*
/**
 * History screen showing all past workout sessions.
 * Displays workouts in reverse chronological order.
 * Shows empty state when no workouts have been completed yet.
 */
@Composable
fun HistoryScreen(
    workoutViewModel: WorkoutViewModel = viewModel()
) {
    val workouts by workoutViewModel.allWorkouts.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.history),
            color = textPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.your_past_workouts),
            color = textSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (workouts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_workouts),
                    color = textSecondary,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn {
                items(workouts) { workout ->
                    WorkoutHistoryCard(workout = workout)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
/**
 * Card component displaying a single past workout.
 * Shows workout name, date and total volume lifted in kilograms.
 * Total volume is calculated as sum of (weight * reps) for all sets.
 */
@Composable
fun WorkoutHistoryCard(
    workout: WorkoutEntity,
    workoutViewModel: WorkoutViewModel = viewModel()
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = dateFormat.format(Date(workout.date))
    var totalVolume by remember { mutableStateOf(0f) }

    LaunchedEffect(workout.id) {
        totalVolume = workoutViewModel.getTotalVolume(workout.id) ?: 0f
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = workout.name,
                    color = textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    color = textSecondary,
                    fontSize = 14.sp
                )
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(
                    text = "${totalVolume.toInt()} kg",
                    color = greenColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}