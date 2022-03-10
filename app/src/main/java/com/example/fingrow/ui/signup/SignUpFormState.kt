package com.example.fingrow.ui.signup

/**
 * Data validation state of the signup form.
 */
data class SignUpFormState(
    val nameError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)