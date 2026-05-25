package com.example.gymtrack.ui.template

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtrack.R
import com.example.gymtrack.data.db.ExerciseLibraryEntity
import com.example.gymtrack.ui.home.backgroundColor
import com.example.gymtrack.ui.home.greenColor
import com.example.gymtrack.ui.home.surfaceColor
import com.example.gymtrack.ui.home.textPrimary
import com.example.gymtrack.ui.home.textSecondary
import com.example.gymtrack.ui.viewmodel.ExerciseLibraryViewModel

@Composable
fun TemplateDetailScreen(
    templateId: Long,
    templateName: String,
    onBack: () -> Unit,
    onStartWorkout: (String, List<String>) -> Unit = { _, _ -> },
    exerciseLibraryViewModel: ExerciseLibraryViewModel = viewModel()
) {
    val exercises by exerciseLibraryViewModel.allExercises.observeAsState(emptyList())
    var showAddExerciseDialog by remember { mutableStateOf(false) }

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
                    text = templateName,
                    color = textPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${exercises.size} exercises",
                    color = textSecondary,
                    fontSize = 14.sp
                )
            }
            Row {
                Button(
                    onClick = { onStartWorkout(templateName, exercises.map { it.name }) },
                    colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.start), color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onBack) {
                    Text(stringResource(R.string.done), color = greenColor, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showAddExerciseDialog = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(stringResource(R.string.add_exercise), color = textPrimary, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseLibraryCard(exercise = exercise)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (showAddExerciseDialog) {
            AddLibraryExerciseDialog(
                onDismiss = { showAddExerciseDialog = false },
                onConfirm = { name, muscleGroup ->
                    exerciseLibraryViewModel.insertExercise(
                        ExerciseLibraryEntity(name = name, muscleGroup = muscleGroup)
                    )
                    showAddExerciseDialog = false
                }
            )
        }
    }
}

@Composable
fun ExerciseLibraryCard(exercise: ExerciseLibraryEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = exercise.name, color = textPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = exercise.muscleGroup, color = textSecondary, fontSize = 14.sp)
            }
            Text("💪", fontSize = 20.sp)
        }
    }
}

@Composable
fun AddLibraryExerciseDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var muscleGroup by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = surfaceColor,
        title = { Text(stringResource(R.string.add_exercise_title), color = textPrimary, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.exercise_name_hint), color = textSecondary) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedBorderColor = greenColor,
                        unfocusedBorderColor = textSecondary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = muscleGroup,
                    onValueChange = { muscleGroup = it },
                    label = { Text("Muscle group", color = textSecondary) },
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
                onClick = { if (name.isNotBlank() && muscleGroup.isNotBlank()) onConfirm(name, muscleGroup) },
                colors = ButtonDefaults.buttonColors(containerColor = greenColor)
            ) {
                Text(stringResource(R.string.add), color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), color = textSecondary)
            }
        }
    )
}