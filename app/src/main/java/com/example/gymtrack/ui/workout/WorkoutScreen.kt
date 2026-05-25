package com.example.gymtrack.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtrack.ui.home.backgroundColor
import com.example.gymtrack.ui.home.greenColor
import com.example.gymtrack.ui.home.surfaceColor
import com.example.gymtrack.ui.home.textPrimary
import com.example.gymtrack.ui.home.textSecondary
import com.example.gymtrack.ui.viewmodel.WorkoutViewModel

@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel = viewModel()
) {
    val activeWorkoutId by workoutViewModel.activeWorkoutId.observeAsState()

    if (activeWorkoutId == null) {
        NoActiveWorkoutScreen(
            onStartWorkout = {
                workoutViewModel.startWorkout("My Workout")
            }
        )
    } else {
        ActiveWorkoutScreen(
            workoutId = activeWorkoutId!!,
            workoutViewModel = workoutViewModel
        )
    }
}

@Composable
fun NoActiveWorkoutScreen(onStartWorkout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No active workout",
            color = textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Start a workout to begin tracking",
            color = textSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStartWorkout,
            colors = ButtonDefaults.buttonColors(containerColor = greenColor),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "▶  Start Workout",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ActiveWorkoutScreen(
    workoutId: Long,
    workoutViewModel: WorkoutViewModel
) {
    val exercises by workoutViewModel.getExercisesForWorkout(workoutId).observeAsState(emptyList())
    var showAddExerciseDialog by remember { mutableStateOf(false) }
    val timerSeconds by workoutViewModel.timerSeconds.observeAsState(0)

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
                    text = "⏱ ${String.format("%02d:%02d", timerSeconds / 60, timerSeconds % 60)}",
                    color = textSecondary,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = { workoutViewModel.finishWorkout() },
                colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Finish", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showAddExerciseDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "+ Add Exercise", color = textPrimary, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    workoutViewModel = workoutViewModel
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        if (showAddExerciseDialog) {
            AddExerciseDialog(
                onDismiss = { showAddExerciseDialog = false },
                onConfirm = { name ->
                    workoutViewModel.insertExercise(
                        com.example.gymtrack.data.db.ExerciseEntity(
                            name = name,
                            workoutId = workoutId
                        )
                    )
                    showAddExerciseDialog = false
                }
            )
        }
    }
}

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var exerciseName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = surfaceColor,
        title = {
            Text(text = "Add Exercise", color = textPrimary, fontWeight = FontWeight.Bold)
        },
        text = {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise name", color = textSecondary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    focusedBorderColor = greenColor,
                    unfocusedBorderColor = textSecondary
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (exerciseName.isNotBlank()) {
                        onConfirm(exerciseName)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = greenColor)
            ) {
                Text("Add", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = textSecondary)
            }
        }
    )
}


@Composable
fun ExerciseCard(
    exercise: com.example.gymtrack.data.db.ExerciseEntity,
    workoutViewModel: WorkoutViewModel
) {
    val sets by workoutViewModel.getSetsForExercise(exercise.id).observeAsState(emptyList())
    var showAddSetDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exercise.name,
                color = textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text("SET", color = textSecondary, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Text("KG", color = textSecondary, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Text("REPS", color = textSecondary, fontSize = 12.sp, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(4.dp))

            sets.forEachIndexed { index, set ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text("${index + 1}", color = textPrimary, modifier = Modifier.weight(1f))
                    Text("${set.weight}", color = textPrimary, modifier = Modifier.weight(1f))
                    Text("${set.reps}", color = textPrimary, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { showAddSetDialog = true }) {
                Text("+ Add Set", color = greenColor)
            }
        }
    }

    if (showAddSetDialog) {
        AddSetDialog(
            onDismiss = { showAddSetDialog = false },
            onConfirm = { weight, reps ->
                workoutViewModel.insertSet(
                    com.example.gymtrack.data.db.SetEntity(
                        weight = weight,
                        reps = reps,
                        exerciseId = exercise.id
                    )
                )
                showAddSetDialog = false
            }
        )
    }
}

@Composable
fun AddSetDialog(
    onDismiss: () -> Unit,
    onConfirm: (Float, Int) -> Unit
) {
    var weight by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = surfaceColor,
        title = {
            Text("Add Set", color = textPrimary, fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)", color = textSecondary) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedBorderColor = greenColor,
                        unfocusedBorderColor = textSecondary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    label = { Text("Reps", color = textSecondary) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedBorderColor = greenColor,
                        unfocusedBorderColor = textSecondary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val w = weight.toFloatOrNull()
                    val r = reps.toIntOrNull()
                    if (w != null && r != null) {
                        onConfirm(w, r)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = greenColor)
            ) {
                Text("Add", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = textSecondary)
            }
        }
    )
}