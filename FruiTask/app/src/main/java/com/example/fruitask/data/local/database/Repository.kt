package com.example.fruitask.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.fruitask.data.local.model.Fruit
import com.example.fruitask.data.local.model.Task

class FruitTaskRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context.applicationContext)

    // Mantener una única instancia de la base de datos
    private var database: SQLiteDatabase? = null

    @Synchronized
    private fun getDatabase(): SQLiteDatabase {
        if (database == null || !database!!.isOpen) {
            database = dbHelper.writableDatabase
        }
        return database!!
    }

    // OPERACIONES FRUIT

    fun insertFruit(fruit: Fruit): Long {
        val db = getDatabase()
        val values = android.content.ContentValues().apply {
            put(DatabaseHelper.C_F_NOMBRE, fruit.nombre)
            put(DatabaseHelper.C_F_TIPO, fruit.tipo.name)
            put(DatabaseHelper.C_F_NIVEL, fruit.nivel)
            put(DatabaseHelper.C_F_EXPERIENCIA, fruit.experiencia)
        }
        return db.insert(DatabaseHelper.TABLE_FRUIT, null, values)
    }

    fun getAllFruits(): List<Fruit> {
        return dbHelper.getAllFruits() // Ya modificado para no cerrar
    }

    fun getFruitById(fruitId: Int): Fruit? {
        return dbHelper.getFruitById(fruitId) // Ya modificado para no cerrar
    }

    fun updateFruit(fruit: Fruit): Int {
        return dbHelper.updateFruit(fruit) // Ya modificado para no cerrar
    }

    fun deleteFruit(fruitId: Int): Int {
        return dbHelper.deleteFruit(fruitId) // Ya modificado para no cerrar
    }

    // OPERACIONES TASK

    fun insertTask(task: Task): Long {
        return dbHelper.insertTask(task) // Ya modificado para no cerrar
    }

    fun getAllTasks(): List<Task> {
        return dbHelper.getAllTasks() // Ya modificado para no cerrar
    }

    fun getTasksForFruit(fruitId: Int): List<Task> {
        return dbHelper.getTasksForFruit(fruitId) // Ya modificado para no cerrar
    }

    fun updateTask(task: Task): Int {
        return dbHelper.updateTask(task) // Ya modificado para no cerrar
    }

    fun deleteTask(taskId: Int): Int {
        return dbHelper.deleteTask(taskId) // Ya modificado para no cerrar
    }

}