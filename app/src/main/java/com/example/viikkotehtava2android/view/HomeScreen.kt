package com.example.viikkotehtava2android.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava2android.viewmodel.TaskViewModel
import com.example.viikkotehtava2android.model.Task

@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onNavigateToCalendar: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    var isAddingTask by remember { mutableStateOf(false) }

    viewModel.editingTask?.let { task ->
        DetailDialog(
            task = task,
            onDismiss = { viewModel.dismissDialog() },
            onConfirm = { updatedTask -> viewModel.updateTask(updatedTask) },
            onDelete = { id -> viewModel.removeTask(id) }
        )
    }

    if (isAddingTask) {
        DetailDialog(
            task = Task(id = 0, title = "", description = "", priority = 1, dueDate = "2026-02-10", done = false),
            onDismiss = { isAddingTask = false },
            onConfirm = { newTask ->
                val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                viewModel.addTask(newTask.copy(id = nextId))
                isAddingTask = false
            },
            onDelete = { isAddingTask = false }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tehtävälista", style = MaterialTheme.typography.headlineMedium)

            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Asetukset",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToCalendar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kalenteri")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { isAddingTask = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Uusi tehtävä")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.sortByDueDate() }, modifier = Modifier.weight(1f)) {
                Text("Järjestä", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = { viewModel.filterByDone(done = true) }, modifier = Modifier.weight(1f)) {
                Text("Vain valmiit", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = { viewModel.resetFilter() }, modifier = Modifier.weight(1f)) {
                Text("Näytä kaikki", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
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

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.done, onCheckedChange = { onToggle() })
            Text(
                text = task.title,
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Poista",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}