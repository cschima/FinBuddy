package com.example.fingrow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
data class Chat (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fromName: String,
    val toName: String,
    val message: String
)