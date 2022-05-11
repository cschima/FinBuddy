package com.example.fingrow.data.goals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals_table")
data class Goal (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val user: String,
    val title: String,
    val total_amount: Double,
    val start_date: String,
    val end_date: String,
    val current_amount: Double
)