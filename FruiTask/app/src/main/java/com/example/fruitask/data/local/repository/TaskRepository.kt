package com.example.fruitask.data.local.repository

import com.example.fruitask.data.local.dao.TaskDao
import com.example.fruitask.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    fun getTasksForFruit(fruitId: Int): Flow<List<TaskEntity>> {
        return taskDao.getTasksForFruit(fruitId)
    }

    suspend fun insertarTask(task: TaskEntity) {
        taskDao.insertarTask(task)
    }
}
