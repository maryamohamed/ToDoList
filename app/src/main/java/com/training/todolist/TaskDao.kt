package com.training.todolist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.training.todolist.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task : Task)

    @Update
    suspend fun updateTask(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks() : LiveData<List<Task>>

    // Function to get tasks by category and return LiveData
    @Query("SELECT * FROM tasks WHERE category = :category")
    fun getTasksByCategory(category : String) : LiveData<List<Task>>
}
