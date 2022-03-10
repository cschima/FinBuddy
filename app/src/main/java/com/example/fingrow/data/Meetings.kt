package com.example.fingrow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings_table")
data class Meetings (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val consultantName: String,
    val meetingDate: String
)