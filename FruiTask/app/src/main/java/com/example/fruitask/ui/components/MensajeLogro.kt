package com.example.fruitask.ui.components

import com.example.fruitask.data.local.model.TipoFruit

fun getMensajeLogro(fruit: TipoFruit?, nivel: Int): String {
    return when (fruit) {
        TipoFruit.KIWI -> "Nivel $nivel: ¡Tu kiwi está que rebosa kiwitud!"
        TipoFruit.MANZANA -> "Nivel $nivel: ¡Tu manzana está más brillante que nunca!"
        TipoFruit.SANDIA -> "Nivel $nivel: ¡Esta sandía viene con más ritmo que nunca!"
        else -> "Nivel $nivel alcanzado!"
    }
}