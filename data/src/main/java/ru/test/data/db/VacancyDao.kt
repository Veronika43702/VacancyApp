package ru.test.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.test.data.entity.VacancyEntity
import java.util.UUID

@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancies_table")
    fun getAll(): LiveData<List<VacancyEntity>>

    @Query("SELECT * FROM vacancies_table WHERE id = :id")
    fun getById(id: UUID): VacancyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<VacancyEntity>)
}