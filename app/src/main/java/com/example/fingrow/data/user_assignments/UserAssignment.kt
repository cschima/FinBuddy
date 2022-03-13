package com.example.fingrow.data.user_assignments

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.fingrow.data.users.User

@Entity(tableName = "user_assignments_table")
data class UserAssignment (
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