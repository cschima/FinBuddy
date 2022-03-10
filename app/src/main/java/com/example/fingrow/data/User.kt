package com.example.fingrow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val userType: String,
    val email: String,
    val password: String
)

/*

General Tables:
Users
Assignments

Per Client:
Money Flow
Meetings
Chat
Goals
Files

*/