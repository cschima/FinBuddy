package com.example.fingrow.data.users

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    // OnConflictStrategy.IGNORE: if there is exactly the same user, then ignore it
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    @Transaction
    @Query("SELECT COUNT(*) FROM users_table")
    suspend fun userCount(): Int

    @Transaction
    @Query("SELECT * FROM users_table WHERE email = :email")
    suspend fun findUser(email: String): User?

    @Delete
    fun delete(user: User)

    @Transaction
    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun readAllUserData(): LiveData<List<User>>

    // Transactions makes sure there aren't any multithreading issues
    @Transaction
    @Query("SELECT * FROM users_table WHERE email = :email")
    fun getUserData(email: String): LiveData<List<User>>

}