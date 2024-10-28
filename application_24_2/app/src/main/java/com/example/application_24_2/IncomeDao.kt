package com.example.application_24_2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: Income)

    @Update
    suspend fun update(income: Income)

    @Delete
    suspend fun delete(income: Income)

    @Query("SELECT * FROM income_table")
    suspend fun getAllIncomes(): List<Income>
}