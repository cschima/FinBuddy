package com.example.fingrow.ui.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.fingrow.R
import com.example.fingrow.data.LoginRepository
import com.example.fingrow.data.Result

import com.example.fingrow.data.model.LoggedInUserView

class SignUpViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _signUpForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _signUpForm

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult

    fun signUp(context: Context, name: String, username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.signUp(context, name, username, password)

        if (result is Result.Success) {
            _signUpResult.value =
                SignUpResult(success = LoggedInUserView(displayName = name))
        } else {
            _signUpResult.value = SignUpResult(error = R.string.sign_up_failed)
        }
    }

    fun signUpDataChanged(name: String, username: String, password: String) {
        if (!isNameValid(name)) {
            _signUpForm.value = SignUpFormState(nameError = R.string.invalid_name)
        }
        if (!isUserNameValid(username)) {
            _signUpForm.value = SignUpFormState(usernameError = R.string.invalid_username)
        }
        if (!isPasswordValid(password)) {
            _signUpForm.value = SignUpFormState(passwordError = R.string.invalid_password)
        }
        if (isNameValid(name) && isUserNameValid(username) && isPasswordValid(password)) {
            _signUpForm.value = SignUpFormState(isDataValid = true)
        }
    }

    // A placeholder name validation check
    private fun isNameValid(name: String): Boolean {
        return (name.isNotBlank())
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}