package com.training.todolist.model

import androidx.lifecycle.LiveData
import com.training.todolist.data.Task
import com.training.todolist.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    // Function to get tasks by category and return LiveData
    fun getTasksByCategory(category: String): LiveData<List<Task>> {
        return taskDao.getTasksByCategory(category)
    }
}
