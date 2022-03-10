package com.example.fingrow.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    // OnConflictStrategy.IGNORE: if there is exactly the same user, then ignore it

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Delete
    fun delete(user:User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUserData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE firstName = :firstName AND lastName = :lastName")
    fun readAllUserAssignmentsData(firstName: String, lastName: String): LiveData<List<UserAssignments>>

    // Transactions makes sure there aren't any multithreading issues
    @Transaction
    @Query("SELECT * FROM user_table WHERE firstName = :firstName AND lastName = :lastName")
    fun getUserData(firstName: String, lastName: String): LiveData<List<User>>

}