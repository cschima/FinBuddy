package com.example.fingrow.data.users

import android.util.Log
import androidx.lifecycle.LiveData
import java.lang.Exception

// A repository class abstracts access to multiple data sources
class UserRepository(private val userDao: UserDao) {
    val readAllData : LiveData<List<User>> = userDao.readAllUserData()

    suspend fun addUser(user: User): Boolean {
        try {
            Log.d("thisismine", userDao.addUser(user).toString())
        } catch (ex: Exception) {
            Log.d("thisismine", ex.toString())
        }
        return true
    }

    suspend fun userCount(): Int {
        return userDao.userCount()
    }

    suspend fun findUser(email: String): User? {
        return userDao.findUser(email)
    }
}