package com.example.fingrow.data.goals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GoalViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<Goal>>

    private val repository: GoalRepository

    init {
        val goalDao = GoalDatabase.getDatabase(application).goalDao()
        repository = GoalRepository(goalDao)
        readAllData = repository.readAllData
    }

    fun addGoal(goal: Goal): Boolean {
        var success = true
        viewModelScope.launch {
            success = repository.addGoal(goal)
        }
        return success
    }

    fun getAllUserGoals(email: String): List<Goal> {
        return runBlocking {
            repository.getAllUserGoals(email)
        }
    }

    fun userGoalCount(email: String): Int {
        return runBlocking {
            repository.userGoalCount(email)
        }
    }

    fun findGoal(id: Int): Goal? {
        return runBlocking {
            repository.findGoal(id)
        }
    }

    fun updateGoal(goal: Goal): Boolean {
        var success = true
        viewModelScope.launch {
            success = repository.updateGoal(goal)
        }
        return success
    }
}