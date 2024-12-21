package com.aplicacionTask.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aplicacionTask.data.local.entity.Task
@Dao
interface TaskDao {

    @Query("select * from task")
    suspend fun listTask(): List<Task>

    @Query("select * from task where id = :taskId")
    suspend fun getTaskId(taskId: Long): Task

    @Insert
    suspend fun newTask(task: Task) : Long

        @Update
        suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}