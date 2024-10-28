package com.example.application_24_2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_table")
data class Income(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val year: Int,
    val month: Int,
    val day: Int,
    val description: String,
    val amount: Double
)