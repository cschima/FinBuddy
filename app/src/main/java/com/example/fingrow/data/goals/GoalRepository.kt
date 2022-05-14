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

    suspend fun getAllActiveUserGoals(email: String): List<Goal> {
        return goalDao.getAllActiveUserGoals(email)
    }

    suspend fun activeUserGoalCount(email: String): Int {
        return goalDao.activeUserGoalCount(email)
    }

    suspend fun getActiveSavings(email: String): Int {
        return goalDao.getActiveSavings(email)
    }

    suspend fun getActiveTotal(email: String): Int {
        return goalDao.getActiveTotal(email)
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