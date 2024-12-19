package com.aplicacionTask.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.aplicacionTask.data.local.dao.TaskDao
import com.aplicacionTask.data.local.entity.Task

@Database(entities = [Task :: class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun taskDao(): TaskDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase (context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}