package com.example.fingrow.data.users

import androidx.lifecycle.LiveData

// A repository class abstracts access to multiple data sources
class UserRepository(private val userDao: UserDao) {
    val readAllData : LiveData<List<User>> = userDao.readAllUserData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun findUser(userName: String, password: String): Int {
        return userDao.findUser(userName, password)
    }
}