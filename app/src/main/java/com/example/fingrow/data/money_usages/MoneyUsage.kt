package com.example.fingrow.data.money_usages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money_usages_table")
data class MoneyUsage (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)