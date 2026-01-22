package com.example.viikkotehtava2android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava2android.domain.TaskViewModel
import com.example.viikkotehtava2android.domain.Task


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {
    val taskList = viewModel.tasks
    var newTaskTitle by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Viikkotehtävä 2: Tehtävälista",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("uusi tehtävä") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTaskTitle.isNotBlank()) {
                    val newTask = Task(
                        id = (taskList.maxOfOrNull { it.id } ?: 0) + 1,
                        title = newTaskTitle,
                        description = "",
                        priority = 1,
                        dueDate = "2026-05-05",
                        done = false
                    )
                    viewModel.addTask(newTask)
                    newTaskTitle = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.sortByDueDate() }) {
            Text("Sort by Date")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList) { task ->
                TaskRow(
                    task = task,
                    onToggle = { viewModel.toggleDone(task.id) },
                    onDelete = { viewModel.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggle() }
            )

            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Poista tehtävä",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}