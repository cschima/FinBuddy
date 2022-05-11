package com.example.fingrow.data.users

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_table", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val start_date: String,
    val total_saved: Int,
    val last_month_saved: Int,
    val this_month_saved: Int,
    val last_month_updated: String
)