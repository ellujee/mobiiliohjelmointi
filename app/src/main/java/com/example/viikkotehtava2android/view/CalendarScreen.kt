package com.example.viikkotehtava2android.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava2android.viewmodel.TaskViewModel

@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val groupedTasks = tasks.groupBy { it.dueDate }

    viewModel.editingTask?.let { task ->
        DetailDialog(
            task = task,
            onDismiss = { viewModel.dismissDialog() },
            onConfirm = { updatedTask -> viewModel.updateTask(updatedTask) },
            onDelete = { id -> viewModel.removeTask(id) }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Kalenteri", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = onNavigateBack) {
                Text("Takaisin")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            groupedTasks.forEach { (date, tasksInDate) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    HorizontalDivider()
                }
                items(tasksInDate) { task ->
                    TaskRow(
                        task = task,
                        onToggle = { viewModel.toggleDone(task.id) },
                        onDelete = { viewModel.removeTask(task.id) },
                        onClick = { viewModel.editTask(task) }
                    )
                }
            }
        }
    }
}