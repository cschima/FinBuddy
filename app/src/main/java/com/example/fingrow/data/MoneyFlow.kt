package com.example.fingrow.data

import androidx.room.PrimaryKey

data class MoneyFlow (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)