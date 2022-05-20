package com.example.fingrow.data.users

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_table", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey val email: String,
    val name: String,
    val password: String,
    val start_date: String,
    val total_saved: Double,
    val this_month_saved: Double,
    val last_month_updated: String
)