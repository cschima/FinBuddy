package com.example.fingrow.data.goals

import androidx.lifecycle.LiveData
import java.lang.Exception

// A repository class abstracts access to multiple data sources
class GoalRepository(private val goalDao: GoalDao) {
    val readAllData: LiveData<List<Goal>> = goalDao.readAllGoalData()

    suspend fun addGoal(goal: Goal): Boolean {
        try {
            goalDao.addGoal(goal).toString()
        } catch (ex: Exception) {
            ex.toString()
        }
        return true
    }

    suspend fun getAllUserGoals(email: String): List<Goal> {
        return goalDao.getAllUserGoals(email)
    }

    suspend fun userGoalCount(email: String): Int {
        return goalDao.userGoalCount(email)
    }

    suspend fun findGoal(id: Int): Goal? {
        return goalDao.findGoal(id)
    }

    suspend fun updateGoal(goal: Goal): Boolean {
        try {
            goalDao.updateGoal(goal)
        } catch (ex: Exception) {
            ex.toString()
        }
        return true
    }
}