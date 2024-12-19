package com.aplicacionTask.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.aplicacionTask.data.local.entity.Task
import com.example.task.databinding.ItemTareaBinding
import java.security.PrivateKey

class TaskAdapter (
    private val tasks : List<Task>,
    private val onDeleteClick : (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =  ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int){
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: ItemTareaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task){
            binding.titulo.text = task.title
            binding.descripcion.text = task.description

            // aca se agrega el boton de eliminar
            binding.root.setOnClickListener{
                onDeleteClick(task)
            }
        }
    }

}