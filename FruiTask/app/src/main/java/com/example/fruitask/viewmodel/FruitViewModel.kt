package com.example.fruitask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fruitask.data.model.Fruit

class FruitViewModel : ViewModel() {

    val fruitList = MutableLiveData<List<Fruit>>()

    fun loadFruits() {
        fruitList.value = listOf(

        )
    }
}
