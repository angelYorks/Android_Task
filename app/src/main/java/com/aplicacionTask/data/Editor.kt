package com.aplicacionTask.data

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

    private val repository: TaskRepository by lazy {
        val dataBase = AppDataBase.getDataBase(applicationContext)
        TaskRepository(dataBase.taskDao())
    }

    // instancia el viewmodel a partir del repositorio instanciado
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(repository)
    }

    private var taskId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editor)

        taskId = intent.getLongExtra("TASK_ID", -1L)

        val descripcion = findViewById<EditText>(R.id.task_description)
        val titulo = findViewById<EditText>(R.id.task_titler)
        val contenido: TextView = findViewById<TextView>(R.id.mostrar_task)

        // Mostrar la información de la tarea en el TextView
        contenido.text = "Contenido del task: ID $taskId - " + titulo.text.toString() + descripcion.text.toString()

        // Si taskId es válido, se carga la tarea
        if (taskId != -1L) {
            loadTaskData(taskId)
        }

        // Configuración de la vista para el sistema de barras de la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadTaskData(taskId: Long) {
        // Cargar la tarea por ID desde el ViewModel
        taskViewModel.getTaskbyId(taskId)

        // Observar el LiveData de la tarea
        taskViewModel.task.observe(this) { task ->
            task?.let {
                val descripcion = findViewById<EditText>(R.id.task_description)
                val titulo = findViewById<EditText>(R.id.task_titler)

                // Actualizar los EditText con los datos de la tarea
                descripcion.setText(it.description)
                titulo.setText(it.title)
            }
        }
    }

    fun updateTask(taskId: Long, descripcion: String, titulo: String) {
        val newTask = Task(id = taskId, title = titulo, description = descripcion)
        taskViewModel.updateTask(newTask)
    }

    // Sobrescribe el método onBackPressed para guardar los cambios antes de salir
    override fun onBackPressed() {
        val descripcion = findViewById<EditText>(R.id.task_description)
        val titulo = findViewById<EditText>(R.id.task_titler)

        // Verifica que taskId no sea -1 (lo que indica que no es una tarea nueva)
        if (taskId != -1L) {
            // Obtén los valores actuales de los campos de texto
            val updatedTitle = titulo.text.toString()
            val updatedDescription = descripcion.text.toString()

            // Llama a la función para actualizar la tarea
            updateTask(taskId, updatedDescription, updatedTitle)

            // Muestra un mensaje de éxito
            Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show()
        }

        // Llama al método de retroceso predeterminado para cerrar la actividad
        super.onBackPressed()
    }
}