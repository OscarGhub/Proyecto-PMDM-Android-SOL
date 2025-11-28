// Archivo: MyViewModel.kt
package com.example.fruitask.data.local.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fruitask.data.local.model.Fruit
import com.example.fruitask.data.local.model.Task
import com.example.fruitask.data.local.model.TipoFruit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FruitTaskRepository(application.applicationContext)

    private val _fruitList = MutableLiveData<List<Fruit>>()
    val fruitList: LiveData<List<Fruit>> = _fruitList

    private val _activeFruit = MutableLiveData<Fruit?>()
    val activeFruit: LiveData<Fruit?> = _activeFruit

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    init {
        cargarTodasLasFrutas()
        cargarTodasLasTareas()
    }

    // --- LECTURA ---

    fun cargarTodasLasFrutas() {
        // Ejecutar en hilo de fondo para no bloquear la UI
        viewModelScope.launch(Dispatchers.IO) {
            val frutas = repository.getAllFruits()
            _fruitList.postValue(frutas)
            _activeFruit.postValue(frutas.firstOrNull()) // Establece la primera como la activa
        }
    }

    fun cargarTodasLasTareas() {
        viewModelScope.launch(Dispatchers.IO) {
            val tareas = repository.getAllTasks()
            _taskList.postValue(tareas)
        }
    }

    // --- ACCIONES DE FRUTA ---

    fun insertNewInitialFruit(nombre: String, tipoStr: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val tipoEnum = when (tipoStr) {
                "Sandia" -> TipoFruit.SANDIA
                "Kiwi" -> TipoFruit.KIWI
                "Manzana" -> TipoFruit.MANZANA
                else -> TipoFruit.SANDIA
            }

            val initialFruit = Fruit(
                id = 0,
                nombre = nombre,
                tipo = tipoEnum,
                nivel = 1,
                experiencia = 0.0
            )
            repository.insertFruit(initialFruit)
            cargarTodasLasFrutas()
        }
    }

    // --- ACCIONES DE TASK ---

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
            cargarTodasLasTareas()
        }
    }

    fun completarTarea(task: Task, onLevelUp: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            var subeNivel = false

            // 1. Marcar tarea como hecha
            val updatedTask = task.copy(tareaHecha = true)
            repository.updateTask(updatedTask)

            // 2. Obtener la fruta activa
            val fruit = activeFruit.value

            if (fruit != null) {
                val nuevaExp = fruit.experiencia + 20.0

                if (nuevaExp >= 100) {
                    // SUBE DE NIVEL
                    val frutaActualizada = fruit.copy(
                        nivel = fruit.nivel + 1,
                        experiencia = 0.0
                    )
                    repository.updateFruit(frutaActualizada)
                    subeNivel = true
                } else {
                    // SOLO SUMA XP
                    val frutaActualizada = fruit.copy(
                        experiencia = nuevaExp
                    )
                    repository.updateFruit(frutaActualizada)
                }
                cargarTodasLasFrutas()
            }

            cargarTodasLasTareas()
            withContext(Dispatchers.Main) {
                onLevelUp(subeNivel)
            }
        }
    }


}