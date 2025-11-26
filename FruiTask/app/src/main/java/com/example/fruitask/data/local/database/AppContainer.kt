package com.example.fruitask.data.local.database

import android.content.Context
import com.example.fruitask.data.local.repository.FruitRepository
interface AppContainer {
    val fruitRepository: FruitRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context = context)
    }

    override val fruitRepository: FruitRepository by lazy {
        FruitRepository(database.fruitDao())
    }
}