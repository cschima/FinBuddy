package com.example.fingrow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Contains the databade holder and serves as the main access point for the underlying
// connection to your app's persisted, relational data
// Set exportSchema to true if you want an export of your database schema in your foler
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    // returns our userDao
    abstract fun userDao(): UserDao

    // object visible to other classes
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // checks if there is an instance of our database
        // if there is, then retun it,
        // else create one
        // we only want one instance of our room database because
        // of performace issues with multiple instances
        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}