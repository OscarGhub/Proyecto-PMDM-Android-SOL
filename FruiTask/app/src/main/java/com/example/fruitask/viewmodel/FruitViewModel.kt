package com.example.fruitask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitask.data.local.database.FruitEntity
import com.example.fruitask.data.local.repository.FruitRepository
import com.example.fruitask.data.model.FruitType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FruitViewModel(private val repository: FruitRepository) : ViewModel() {

    val fruitList: StateFlow<List<FruitEntity>> = repository.getAllFruits()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addFruit(id: Int, nombre: String, tipo: FruitType) {
        viewModelScope.launch {
            val newFruit = FruitEntity(id = id, nombre = nombre, tipo = tipo)
            repository.insertarFruit(newFruit)
        }
    }
}