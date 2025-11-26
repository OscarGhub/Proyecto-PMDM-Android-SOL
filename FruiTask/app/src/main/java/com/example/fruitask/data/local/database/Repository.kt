package com.example.fruitask.data.local.database

import android.content.Context
import com.example.fruitask.data.local.model.Fruit
import com.example.fruitask.data.local.model.Task

class FruitTaskRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // OPERACIONES FRUIT

    fun insertFruit(fruit: Fruit): Long {
        return dbHelper.insertFruit(fruit)
    }

    fun getAllFruits(): List<Fruit> {
        return dbHelper.getAllFruits()
    }

    fun getFruitById(fruitId: Int): Fruit? {
        return dbHelper.getFruitById(fruitId)
    }

    fun updateFruit(fruit: Fruit): Int {
        return dbHelper.updateFruit(fruit)
    }

    fun deleteFruit(fruitId: Int): Int {
        return dbHelper.deleteFruit(fruitId)
    }

    // OPERACIONES TASK

    fun insertTask(task: Task): Long {
        return dbHelper.insertTask(task)
    }

    fun getAllTasks(): List<Task> {
        return dbHelper.getAllTasks()
    }

    fun getTasksForFruit(fruitId: Int): List<Task> {
        return dbHelper.getTasksForFruit(fruitId)
    }

    fun updateTask(task: Task): Int {
        return dbHelper.updateTask(task)
    }

    fun deleteTask(taskId: Int): Int {
        return dbHelper.deleteTask(taskId)
    }
}