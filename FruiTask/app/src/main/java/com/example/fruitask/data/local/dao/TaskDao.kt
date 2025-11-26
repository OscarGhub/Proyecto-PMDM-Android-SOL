package com.example.fruitask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fruitask.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertarTask(task: TaskEntity)

    @Query("SELECT * FROM task ORDER BY fechaActividad ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM task WHERE fruitId = :fruitId ORDER BY fechaActividad ASC")
    fun getTasksForFruit(fruitId: Int): Flow<List<TaskEntity>>
}