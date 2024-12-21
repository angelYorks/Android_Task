package com.aplicacionTask.data.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplicacionTask.data.local.entity.Task
import com.aplicacionTask.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository): ViewModel() {
    private val _taks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _taks

    fun fetchTasks(){
        viewModelScope.launch {
            val taskList = taskRepository.getAllTask()
            _taks.postValue(taskList)
        }
    }

    fun insertTask(task: Task, onTaskInserted: (Long) -> Unit){
        viewModelScope.launch {
            val taskId = taskRepository.insertTask(task)
            onTaskInserted(taskId)
            fetchTasks()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            taskRepository.updateTask(task)
            fetchTasks()
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            fetchTasks()
        }
    }

}