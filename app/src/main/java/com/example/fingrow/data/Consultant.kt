package com.example.fingrow.data

import androidx.room.Entity

import androidx.room.PrimaryKey

@Entity(tableName = "consultant_table")
data class Consultant (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val email: String,
    val password: String
)