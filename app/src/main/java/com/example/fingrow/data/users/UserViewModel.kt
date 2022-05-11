package com.example.fingrow.data.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<User>>

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User): Boolean {
        var success = true
        viewModelScope.launch {
            success = repository.addUser(user)
        }
        return success
    }

    fun findUser(email: String): User? {
        return runBlocking {
            repository.findUser(email)
        }
    }

    fun updateUser(user: User): Boolean {
        var success = true
        viewModelScope.launch {
            success = repository.updateUser(user)
        }
        return success
    }
}