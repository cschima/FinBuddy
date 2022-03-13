package com.example.fingrow.data.goals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals_table")
data class Goal (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val goalName: String,
    val startDate: String,
    val endDate: String
)