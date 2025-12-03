package com.example.fruitask.data.local.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.fruitask.data.local.model.Fruit
import com.example.fruitask.data.local.model.Task
import com.example.fruitask.data.local.model.TipoActividad
import com.example.fruitask.data.local.model.TipoFruit
import java.util.Date

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context.applicationContext,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        const val DATABASE_NAME = "fruitask.db"
        const val DATABASE_VERSION = 1

        const val TABLE_FRUIT = "fruit"
        const val C_F_ID = "id"
        const val C_F_NOMBRE = "nombre"
        const val C_F_TIPO = "tipo"
        const val C_F_NIVEL = "nivel"
        const val C_F_EXPERIENCIA = "experiencia"

        // Constantes para la tabla TASK
        const val TABLE_TASK = "task"
        const val C_T_ID = "id"
        const val C_T_NOMBRE = "nombreTarea"
        const val C_T_DESCRIPCION = "descripcionTarea"
        const val C_T_TIPO_ACT = "tipoActividad"
        const val C_T_FECHA = "fechaActividad"
        const val C_T_ESTADO_ACT = "estadoActividad"
        const val C_T_TAREA_HECHA = "tareaHecha"
        const val C_T_FRUIT_ID = "fruitId"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tabla FRUIT
        val createTableFruit = """
         CREATE TABLE $TABLE_FRUIT (
         $C_F_ID INTEGER PRIMARY KEY AUTOINCREMENT,
         $C_F_NOMBRE TEXT,
         $C_F_TIPO TEXT,
         $C_F_NIVEL INTEGER,
         $C_F_EXPERIENCIA REAL
         )
         """
        db.execSQL(createTableFruit)

        // Tabla TASK
        val createTableTask = """
         CREATE TABLE $TABLE_TASK (
         $C_T_ID INTEGER PRIMARY KEY AUTOINCREMENT,
         $C_T_NOMBRE TEXT,
         $C_T_DESCRIPCION TEXT,
         $C_T_TIPO_ACT TEXT,
         $C_T_FECHA INTEGER,
         $C_T_ESTADO_ACT INTEGER,
         $C_T_TAREA_HECHA INTEGER,
         $C_T_FRUIT_ID INTEGER,
         FOREIGN KEY($C_T_FRUIT_ID) REFERENCES $TABLE_FRUIT($C_F_ID) ON DELETE CASCADE
         )
         """
        db.execSQL(createTableTask)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FRUIT")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    // Recuperar TODAS las frutas
    fun getAllFruits(): List<Fruit> {
        val fruitList = mutableListOf<Fruit>()
        val db = readableDatabase  // NO cerrar después

        val cursor: Cursor = db.query(
            TABLE_FRUIT,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(C_F_ID))
                val nombre = it.getString(it.getColumnIndexOrThrow(C_F_NOMBRE))
                val tipoStr = it.getString(it.getColumnIndexOrThrow(C_F_TIPO))
                val nivel = it.getInt(it.getColumnIndexOrThrow(C_F_NIVEL))
                val experiencia = it.getDouble(it.getColumnIndexOrThrow(C_F_EXPERIENCIA))

                fruitList.add(
                    Fruit(
                        id = id,
                        nombre = nombre,
                        tipo = TipoFruit.valueOf(tipoStr),
                        nivel = nivel,
                        experiencia = experiencia
                    )
                )
            }
        }
        return fruitList
    }

    // --- Inserción de FRUIT ---
    fun insertFruit(fruit: Fruit): Long {
        val db = this.writableDatabase  // NO cerrar después
        val values = ContentValues().apply {
            put(C_F_NOMBRE, fruit.nombre)
            put(C_F_TIPO, fruit.tipo.name)
            put(C_F_NIVEL, fruit.nivel)
            put(C_F_EXPERIENCIA, fruit.experiencia)
        }
        val id = db.insert(TABLE_FRUIT, null, values)
        return id
    }

    // --- Actualización de FRUIT ---
    fun updateFruit(fruit: Fruit): Int {
        val db = this.writableDatabase  // NO cerrar después
        val values = ContentValues().apply {
            put(C_F_NOMBRE, fruit.nombre)
            put(C_F_TIPO, fruit.tipo.name)
            put(C_F_NIVEL, fruit.nivel)
            put(C_F_EXPERIENCIA, fruit.experiencia)
        }
        val selection = "$C_F_ID = ?"
        val selectionArgs = arrayOf(fruit.id.toString())

        val count = db.update(TABLE_FRUIT, values, selection, selectionArgs)
        return count
    }

    // --- Borrado de FRUIT ---
    fun deleteFruit(fruitId: Int): Int {
        val db = this.writableDatabase  // NO cerrar después
        val selection = "$C_F_ID = ?"
        val selectionArgs = arrayOf(fruitId.toString())

        val deletedRows = db.delete(TABLE_FRUIT, selection, selectionArgs)
        return deletedRows
    }

    // Fruta por Id
    fun getFruitById(fruitId: Int): Fruit? {
        val db = readableDatabase  // NO cerrar después
        val selection = "$C_F_ID = ?"
        val selectionArgs = arrayOf(fruitId.toString())

        val cursor: Cursor = db.query(TABLE_FRUIT, null, selection, selectionArgs, null, null, null)

        cursor.use {
            if (it.moveToFirst()) {
                val nombre = it.getString(it.getColumnIndexOrThrow(C_F_NOMBRE))
                val tipoStr = it.getString(it.getColumnIndexOrThrow(C_F_TIPO))
                val nivel = it.getInt(it.getColumnIndexOrThrow(C_F_NIVEL))
                val experiencia = it.getDouble(it.getColumnIndexOrThrow(C_F_EXPERIENCIA))

                return Fruit(
                    id = fruitId,
                    nombre = nombre,
                    tipo = TipoFruit.valueOf(tipoStr),
                    nivel = nivel,
                    experiencia = experiencia
                )
            }
        }
        return null
    }

    // --- Inserción de TASK ---
    fun insertTask(task: Task): Long {
        val db = this.writableDatabase  // NO cerrar después
        val values = ContentValues().apply {
            put(C_T_NOMBRE, task.nombreTarea)
            put(C_T_DESCRIPCION, task.descripcionTarea)
            put(C_T_TIPO_ACT, task.tipoActividad.name)
            put(C_T_FECHA, task.fechaActividad.time)

            put(C_T_ESTADO_ACT, if (task.estadoActividad) 1 else 0)
            put(C_T_TAREA_HECHA, if (task.tareaHecha) 1 else 0)

            put(C_T_FRUIT_ID, task.fruitId)
        }
        val id = db.insert(TABLE_TASK, null, values)
        return id
    }

    // --- Actualización de TASK ---
    fun updateTask(task: Task): Int {
        val db = this.writableDatabase  // NO cerrar después
        val values = ContentValues().apply {
            put(C_T_NOMBRE, task.nombreTarea)
            put(C_T_DESCRIPCION, task.descripcionTarea)
            put(C_T_TIPO_ACT, task.tipoActividad.name)
            put(C_T_FECHA, task.fechaActividad.time)
            put(C_T_ESTADO_ACT, if (task.estadoActividad) 1 else 0)
            put(C_T_TAREA_HECHA, if (task.tareaHecha) 1 else 0)
        }
        val selection = "$C_T_ID = ?"
        val selectionArgs = arrayOf(task.id.toString())

        val count = db.update(TABLE_TASK, values, selection, selectionArgs)
        return count
    }

    // --- Eliminación de TASK ---
    fun deleteTask(taskId: Int): Int {
        val db = this.writableDatabase  // NO cerrar después
        val selection = "$C_T_ID = ?"
        val selectionArgs = arrayOf(taskId.toString())

        val deletedRows = db.delete(TABLE_TASK, selection, selectionArgs)
        return deletedRows
    }

    // --- Lectura de Tareas por Fruta ---
    fun getTasksForFruit(fruitId: Int): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase  // NO cerrar después

        val selection = "$C_T_FRUIT_ID = ?"
        val selectionArgs = arrayOf(fruitId.toString())

        val cursor = db.query(TABLE_TASK, null, selection, selectionArgs, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(C_T_ID))
                val nombre = it.getString(it.getColumnIndexOrThrow(C_T_NOMBRE))
                val desc = it.getString(it.getColumnIndexOrThrow(C_T_DESCRIPCION))
                val tipoActStr = it.getString(it.getColumnIndexOrThrow(C_T_TIPO_ACT))
                val fechaLong = it.getLong(it.getColumnIndexOrThrow(C_T_FECHA))
                val estadoActInt = it.getInt(it.getColumnIndexOrThrow(C_T_ESTADO_ACT))
                val tareaHechaInt = it.getInt(it.getColumnIndexOrThrow(C_T_TAREA_HECHA))
                val fId = it.getInt(it.getColumnIndexOrThrow(C_T_FRUIT_ID))

                taskList.add(
                    Task(
                        id = id,
                        nombreTarea = nombre,
                        descripcionTarea = desc,
                        tipoActividad = TipoActividad.valueOf(tipoActStr),
                        fechaActividad = Date(fechaLong),
                        estadoActividad = (estadoActInt == 1),
                        tareaHecha = (tareaHechaInt == 1),
                        fruitId = fId
                    )
                )
            }
        }
        return taskList
    }

    // --- Lectura de TODAS las Tareas ---
    fun getAllTasks(): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase  // NO cerrar después

        val cursor = db.query(TABLE_TASK, null, null, null, null, null, null)

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(C_T_ID))
                val nombre = it.getString(it.getColumnIndexOrThrow(C_T_NOMBRE))
                val desc = it.getString(it.getColumnIndexOrThrow(C_T_DESCRIPCION))
                val tipoActStr = it.getString(it.getColumnIndexOrThrow(C_T_TIPO_ACT))
                val fechaLong = it.getLong(it.getColumnIndexOrThrow(C_T_FECHA))
                val estadoActInt = it.getInt(it.getColumnIndexOrThrow(C_T_ESTADO_ACT))
                val tareaHechaInt = it.getInt(it.getColumnIndexOrThrow(C_T_TAREA_HECHA))
                val fId = it.getInt(it.getColumnIndexOrThrow(C_T_FRUIT_ID))

                taskList.add(
                    Task(
                        id = id,
                        nombreTarea = nombre,
                        descripcionTarea = desc,
                        tipoActividad = TipoActividad.valueOf(tipoActStr),
                        fechaActividad = Date(fechaLong),
                        estadoActividad = (estadoActInt == 1),
                        tareaHecha = (tareaHechaInt == 1),
                        fruitId = fId
                    )
                )
            }
        }
        return taskList
    }
}