package com.example.fingrow

import android.content.Context
import android.util.Patterns
import java.math.BigInteger
import java.security.MessageDigest

class SharedHelper(val context: Context) {

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
                password.contains(Regex(context.getString(R.string.lowercase_regex))) &&
                password.contains(Regex(context.getString(R.string.uppercase_regex))) &&
                password.contains(Regex(context.getString(R.string.number_regex))) &&
                password.contains(Regex(context.getString(R.string.symbol_regex)))
    }

    fun getPasswordErrors(password: String): ArrayList<String> {
        val errors: ArrayList<String> = arrayListOf()
        if (password.length <= 5) {
            errors.add("Must be >5 characters")
        }
        if (!password.contains(Regex(context.getString(R.string.lowercase_regex)))) {
            errors.add("Must contain a lowercase")
        }
        if (!password.contains(Regex(context.getString(R.string.uppercase_regex)))) {
            errors.add("Must contain an uppercase")
        }
        if (!password.contains(Regex(context.getString(R.string.number_regex)))) {
            errors.add("Must contain a number")
        }
        if (!password.contains(Regex(context.getString(R.string.symbol_regex)))) {
            errors.add("Must contain a symbol")
        }
        return errors
    }
}