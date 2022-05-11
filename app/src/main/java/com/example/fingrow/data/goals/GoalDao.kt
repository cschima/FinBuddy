package com.example.fingrow.data.goals

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {

    // OnConflictStrategy.IGNORE: if there is exactly the same user, then ignore it
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addGoal(goal: Goal)

    @Transaction
    @Query("SELECT COUNT(*) FROM goals_table where user = :email")
    suspend fun userGoalCount(email: String): Int

    @Transaction
    @Query("SELECT * FROM goals_table WHERE id = :id")
    suspend fun findGoal(id: Int): Goal?

    @Delete
    fun delete(goal: Goal)

    @Transaction
    @Query("SELECT * FROM goals_table ORDER BY id ASC")
    fun readAllGoalData(): LiveData<List<Goal>>

    @Transaction
    @Query("SELECT * FROM goals_table WHERE user = :email ORDER BY id ASC")
    suspend fun getAllUserGoals(email: String): List<Goal>

    @Update
    suspend fun updateGoal(goal: Goal)
}