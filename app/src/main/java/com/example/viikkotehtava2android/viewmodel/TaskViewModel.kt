package com.example.viikkotehtava2android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viikkotehtava2android.model.Task
import com.example.viikkotehtava2android.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(mockTasks)

    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    var editingTask by mutableStateOf<Task?>(null)
        private set

    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
    }

    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    fun updateTask(updatedTask: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == updatedTask.id) updatedTask else it
        }
        dismissDialog()
    }

    fun editTask(task: Task) {
        editingTask = task
    }

    fun dismissDialog() {
        editingTask = null
    }

    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { it.dueDate }
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = _tasks.value.filter { it.done == done }
    }

    fun resetFilter() {
        _tasks.value = mockTasks
    }
}