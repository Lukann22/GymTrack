package com.example.gymtrack.ui.home

import com.example.gymtrack.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtrack.data.db.WorkoutTemplateEntity
import com.example.gymtrack.ui.viewmodel.WorkoutTemplateViewModel

val backgroundColor = Color(0xFF0A0A0A)
val surfaceColor = Color(0xFF1A1A1A)
val greenColor = Color(0xFF4ADE80)
val textPrimary = Color(0xFFFFFFFF)
val textSecondary = Color(0xFF9CA3AF)
/**
 * Main home screen composable showing quick start templates and workout button.
 * Displays list of user-created workout templates for quick access.
 */
@Composable
fun HomeScreen(
    templateViewModel: WorkoutTemplateViewModel = viewModel(),
    onStartWorkout: () -> Unit = {},
    onTemplateClick: (Long, String) -> Unit = { _, _ -> }


) {
    val templates by templateViewModel.allTemplates.observeAsState(emptyList())
    var showAddTemplateDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            color = textPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.ready_to_crush),
            color = textSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStartWorkout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = greenColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.start_empty_workout),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.quick_start),
                color = textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { showAddTemplateDialog = true }) {
                Text("+ New", color = greenColor)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(templates) { template ->
                TemplateCard(template = template,
                    onTemplateClick = onTemplateClick)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (showAddTemplateDialog) {
            AddTemplateDialog(
                onDismiss = { showAddTemplateDialog = false },
                onConfirm = { name ->
                    templateViewModel.insertTemplate(
                        com.example.gymtrack.data.db.WorkoutTemplateEntity(name = name)
                    )
                    showAddTemplateDialog = false
                }
            )
        }
    }
}
/**
 * Card component displaying a single workout template.
 * Shows template name, exercise count and navigates to template detail on click.
 */
@Composable
fun TemplateCard(template: WorkoutTemplateEntity,onTemplateClick: (Long, String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable { onTemplateClick(template.id, template.name) },
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = template.name,
                    color = textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.exercises_count, template.exerciseCount),
                    color = textSecondary,
                    fontSize = 14.sp
                )
            }
            Text(text = "💪", fontSize = 24.sp)
        }
    }
}

/**
 * Dialog for creating a new workout template/routine.
 * Validates input before confirming to prevent empty template names.
 */
@Composable
fun AddTemplateDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var templateName by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = surfaceColor,
        title = { Text("New Routine", color = textPrimary, fontWeight = FontWeight.Bold) },
        text = {
            OutlinedTextField(
                value = templateName,
                onValueChange = { templateName = it },
                label = { Text("Routine name", color = textSecondary) },
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
                onClick = { if (templateName.isNotBlank()) onConfirm(templateName) },
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