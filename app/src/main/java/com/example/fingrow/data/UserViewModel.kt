package com.example.fingrow.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<User>>

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun findUser(userName: String, password: String): Int {

        var count = 0;
        viewModelScope.launch(Dispatchers.IO) {
            count = repository.findUser(userName, password)
        }
        return 0

    }

//    fun findUser(userName: String, password: String) = viewModelScope.async {
//        repository.findUser(userName, password)
//    }
}