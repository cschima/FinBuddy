package com.example.fingrow.data.schedules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedules_table")
data class Schedule (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val consultantName: String,
    val meetingDate: String
)