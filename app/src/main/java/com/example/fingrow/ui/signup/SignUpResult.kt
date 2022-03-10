package com.example.fingrow.ui.signup

import com.example.fingrow.data.model.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class SignUpResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)