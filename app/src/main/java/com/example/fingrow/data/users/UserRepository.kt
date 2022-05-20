package com.example.fingrow.data.users

import androidx.lifecycle.LiveData
import java.lang.Exception

// A repository class abstracts access to multiple data sources
class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllUserData()

    suspend fun addUser(user: User): Boolean {
        try {
            userDao.addUser(user).toString()
        } catch (ex: Exception) {
            ex.toString()
        }
        return true
    }

    suspend fun findUser(email: String): User? {
        return userDao.findUser(email)
    }

    suspend fun updateUser(user: User): Boolean {
        try {
            userDao.updateUser(user)
        } catch (ex: Exception) {
            ex.toString()
        }
        return true
    }
}