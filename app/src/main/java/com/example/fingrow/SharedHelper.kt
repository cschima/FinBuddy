package com.example.fingrow

import android.content.SharedPreferences
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.data.users.User
import com.example.fingrow.data.users.UserViewModel
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

class SharedHelper(val activity: AppCompatActivity) {

    fun hashPassword(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun isNameValid(name: String): Boolean {
        // TODO
        return (name.isNotBlank())
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 5 &&
                password.contains(Regex(activity.getString(R.string.lowercase_regex))) &&
                password.contains(Regex(activity.getString(R.string.uppercase_regex))) &&
                password.contains(Regex(activity.getString(R.string.number_regex))) &&
                password.contains(Regex(activity.getString(R.string.symbol_regex)))
    }

    fun getPasswordErrors(password: String): ArrayList<String> {
        val errors: ArrayList<String> = arrayListOf()
        if (password.length <= 5) {
            errors.add("Must be >5 characters")
        }
        if (!password.contains(Regex(activity.getString(R.string.lowercase_regex)))) {
            errors.add("Must contain a lowercase")
        }
        if (!password.contains(Regex(activity.getString(R.string.uppercase_regex)))) {
            errors.add("Must contain an uppercase")
        }
        if (!password.contains(Regex(activity.getString(R.string.number_regex)))) {
            errors.add("Must contain a number")
        }
        if (!password.contains(Regex(activity.getString(R.string.symbol_regex)))) {
            errors.add("Must contain a symbol")
        }
        return errors
    }

    fun checkLastMonthUpdated(user: User) {
        val currMonthYear = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString() +
                "/" + Calendar.getInstance().get(Calendar.YEAR).toString()
        if (user.last_month_updated != currMonthYear) {
            val newUser = User(
                user.email,
                user.name,
                user.password,
                user.start_date,
                user.total_saved,
                0.0,
                currMonthYear
            )
            ViewModelProvider(activity)[UserViewModel::class.java].updateUser(newUser)
        }

        val prefs: SharedPreferences = (activity).getSharedPreferences("login",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("this_month_saved", user.this_month_saved.toString())
        editor.putString("total_saved", user.total_saved.toString())
        editor.apply()
    }
}