package com.aplicacionTask.data.repository

import com.aplicacionTask.data.local.dao.TaskDao
import com.aplicacionTask.data.local.entity.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun getAllTask(): List<Task>{
        return taskDao.listTask()
    }

    suspend fun insertTask(task: Task): Long{
        val taskId = taskDao.newTask(task)
        return taskId
    }

    suspend fun updateTask(task : Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

}