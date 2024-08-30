package com.training.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = com.training.todolist.TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    // Function to insert a new task
    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    // Function to delete a task
    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    // Function to filter tasks by category and return LiveData
    fun filterTasksByCategory(category: String): LiveData<List<Task>> {
        return repository.getTasksByCategory(category)
    }

    // Function to update a task
    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }
}
