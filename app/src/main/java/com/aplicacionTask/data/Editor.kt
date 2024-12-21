package com.aplicacionTask.data

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aplicacionTask.data.local.AppDataBase
import com.aplicacionTask.data.local.entity.Task
import com.aplicacionTask.data.repository.TaskRepository
import com.aplicacionTask.data.viewModel.TaskViewModel
import com.aplicacionTask.data.viewModel.TaskViewModelFactory
import com.example.task.R

class Editor : AppCompatActivity() {

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
        setContentView(R.layout.activity_editor)

        val taskId = intent.getLongExtra("TASK_ID",-1)

        val descripcion = findViewById<EditText>(R.id.task_description)

        val titulo = findViewById<EditText>(R.id.task_titler)

        val contenido: TextView = findViewById<TextView>(R.id.mostrar_task)

        contenido.text = "contenido del task: " + taskId + titulo.text.toString() +descripcion.text.toString()

        if(taskId != 1L){

            updateTask(taskId, descripcion.text.toString(), titulo.text.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun updateTask(taskId:Long, descripcion:String, titulo:String){
        val newTask = Task(id =  taskId,
            title = titulo,
            description = descripcion)
        taskViewModel.updateTask(newTask)

    }

}