package com.example.fingrow.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import androidx.room.PrimaryKey

@Entity(tableName = "user_assignments_table")
data class UserAssignments (
    @Embedded val consultant: User,

    @Relation(
        parentColumn = "id",
        entityColumn =  "id"
    )
    val clients: List<User>

//    @PrimaryKey(autoGenerate = false)
//    val userName: String,
//    val consultantName: String,
)