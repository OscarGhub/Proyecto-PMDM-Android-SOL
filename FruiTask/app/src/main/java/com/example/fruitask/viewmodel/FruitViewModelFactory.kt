package com.example.fruitask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fruitask.data.local.repository.FruitRepository

class FruitViewModelFactory(private val repository: FruitRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 1. Verificamos que la clase que se está solicitando sea FruitViewModel
        if (modelClass.isAssignableFrom(FruitViewModel::class.java)) {

            // 2. Si es FruitViewModel, lo creamos pasándole el repositorio.
            @Suppress("UNCHECKED_CAST")
            return FruitViewModel(repository) as T
        }

        // 3. Si se solicita cualquier otra clase, lanzamos una excepción.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}