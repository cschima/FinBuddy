package com.example.fingrow.data.goals

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Contains the database holder and serves as the main access point for the underlying
// connection to your app's persisted, relational data
// Set exportSchema to true if you want an export of your database schema in your folder
@Database(entities = [Goal::class], version = 1, exportSchema = false)
abstract class GoalDatabase: RoomDatabase() {

    // returns our goalDao
    abstract fun goalDao(): GoalDao

    // object visible to other classes
    companion object {
        @Volatile
        private var INSTANCE: GoalDatabase? = null

        // checks if there is an instance of our database
        // if there is, then return it,
        // else create one
        // we only want one instance of our room database because
        // of performance issues with multiple instances
        fun getDatabase(context: Context): GoalDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoalDatabase::class.java,
                    "goal_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}