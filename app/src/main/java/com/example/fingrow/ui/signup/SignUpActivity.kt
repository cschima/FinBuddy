package com.example.fingrow.ui.signup

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.MainActivity
import com.example.fingrow.SharedHelper
import com.example.fingrow.data.users.User
import com.example.fingrow.data.users.UserViewModel
import com.example.fingrow.databinding.ActivitySignUpBinding
import com.example.fingrow.ui.login.LoginActivity
import com.example.fingrow.ui.onboarding.OnboardingActivity
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedHelper: SharedHelper

    private var isValidDetails: Boolean = false

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                signUp(
                    binding.name.text.toString(),
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sharedHelper = SharedHelper(this)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.name
        val email = binding.email
        val password = binding.password

        name.afterTextChanged {
            signUpDataChanged(
                name,
                email,
                password
            )
        }

        email.afterTextChanged {
            signUpDataChanged(
                name,
                email,
                password
            )
        }

        password.apply {
            afterTextChanged {
                signUpDataChanged(
                    name,
                    email,
                    password
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && isValidDetails) {
                    if (isNewUser(email.text.toString())) {
                        onboard()
                    } else {
                        email.error = "A user with that email already exists"
                        showSignUpFailed("A user with that email already exists")
                    }
                }
                false
            }
        }

        binding.nextButton.setOnClickListener {
            if (isNewUser(email.text.toString())) {
                onboard()
            } else {
                email.error = "A user with that email already exists"
                showSignUpFailed("A user with that email already exists")
            }
        }

        binding.logInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpDataChanged(name: EditText, email: EditText, password: EditText) {
        val nameValid = sharedHelper.isNameValid(name.text.toString())
        val emailValid = sharedHelper.isEmailValid(email.text.toString())
        val passwordValid = sharedHelper.isPasswordValid(password.text.toString())
        if (name.text.isNotEmpty() && !nameValid) {
            binding.name.error = "Not a valid name"
        }
        if (email.text.isNotEmpty() && !emailValid) {
            email.error = "Not a valid email address"
        }
        if (password.text.isNotEmpty() && !passwordValid) {
            var errorString = ""
            for (err in sharedHelper.getPasswordErrors(password.text.toString())) {
                errorString += err + "\n"
            }
            password.error = errorString.trim()
        }

        isValidDetails = nameValid && emailValid && passwordValid
        binding.nextButton.isEnabled = isValidDetails
    }

    private fun isNewUser(email: String): Boolean {
        val user = runBlocking { userViewModel.findUser(email.lowercase()) }
        return user == null
    }

    private fun onboard() {
        // TODO: Get info from onboarding screens
        val intent = Intent(this, OnboardingActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun signUp(name: String, email: String, password: String) {
        try {
            val startDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat.getDateInstance() //or use getDateTimeInstance()
            val formattedDate = formatter.format(startDate)
            val user = User(
                0,
                name,
                email.lowercase(),
                sharedHelper.hashPassword(password),
                formattedDate,
                0,
                0,
                0,
                (Calendar.getInstance().get(Calendar.MONTH) + 1).toString() +
                        "/" + Calendar.getInstance().get(Calendar.YEAR).toString()
            )

            userViewModel.addUser(user)
            if (userViewModel.findUser(email.lowercase()) == null) {
                throw IllegalArgumentException()
            }

            // Save user in SharedPreferences
            val prefs: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("name", name)
            editor.putString("email", email)
            editor.putString("start_date", formattedDate)
            editor.putString("total_saved", "0")
            editor.putString("last_month_saved", "0")
            editor.putString("this_month_saved", "0")
            editor.apply()

            updateUiWithUser()
            setResult(Activity.RESULT_OK)
        } catch (e: Throwable) {
            showSignUpFailed("Error signing up")
            setResult(Activity.RESULT_CANCELED)
        }
    }

    private fun updateUiWithUser() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        finish()
    }

    private fun showSignUpFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}