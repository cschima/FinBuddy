package com.example.fingrow.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.fingrow.data.model.LoggedInUser
import java.io.IOException
import java.lang.Exception

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(context: Context, username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            // throw Exception()
            val fakeUser = LoggedInUser(username, "Jane Doe")

            // Save user in SharedPreferences
            val prefs: SharedPreferences = context.getSharedPreferences("login",
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("user", fakeUser.displayName)
            editor.apply()

            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("user", "")
        editor.apply()
    }
}