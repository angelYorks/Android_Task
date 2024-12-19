package com.aplicacionTask.data

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aplicacionTask.data.local.AppDataBase
import com.aplicacionTask.data.local.entity.Task
import com.aplicacionTask.data.repository.TaskRepository
import com.aplicacionTask.data.viewModel.TaskViewModel
import com.aplicacionTask.data.viewModel.TaskViewModelFactory
import com.example.task.R

class Activity : AppCompatActivity() {

    // instancia el repositorio con la base de datos
    private val repository: TaskRepository by lazy{
        val dataBase = AppDataBase.getDataBase(applicationContext)
        TaskRepository(dataBase.taskDao())
    }

    // instancia el viewmodel a partir del repositorio instanciado
    private val taskViewModel: TaskViewModel by viewModels {
       TaskViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_)

        val btnInsert = findViewById<AppCompatButton>(R.id.circular_button)
        btnInsert.setOnClickListener{
            insertTask()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun insertTask(){
        val newTask = Task(
            title = "nueva tarea",
            description = "descripcion de la nueva tarea"
        )

        taskViewModel.insertTask(newTask)

    }

}