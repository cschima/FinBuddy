package com.example.fingrow.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.MainActivity
import com.example.fingrow.R
import com.example.fingrow.data.users.User
import com.example.fingrow.data.users.UserViewModel
import com.example.fingrow.data.LoggedInUser
import com.example.fingrow.databinding.ActivitySignUpBinding
import com.example.fingrow.ui.login.LoginActivity
import com.example.fingrow.ui.onboarding.OnboardingActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: ActivitySignUpBinding

    private var validDetails: Boolean = false

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                signUp(
                    binding.name.text.toString(),
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.name
        val username = binding.username
        val password = binding.password
        val next = binding.nextButton

        name.afterTextChanged {
            signUpDataChanged(
                name.text.toString(),
                username.text.toString(),
                password.text.toString()
            )
        }

        username.afterTextChanged {
            signUpDataChanged(
                name.text.toString(),
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                signUpDataChanged(
                    name.text.toString(),
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && validDetails) {
                    if (isNewUser(username.text.toString())) {
                        onboard()
                    } else {
                        showSignUpFailed("User already exists")
                    }
                }
                false
            }
        }

        next.setOnClickListener {
            if (isNewUser(username.text.toString())) {
                onboard()
            } else {
                showSignUpFailed("User already exists")
            }
        }

        binding.logInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpDataChanged(name: String, username: String, password: String) {
        // TODO
        if (!isNameValid(name)) {
            binding.username.error = "Not a valid name"
        }
        if (!isUserNameValid(username)) {
            binding.username.error = "Not a valid username"
        }
        if (!isPasswordValid(password)) {
            binding.password.error = "Password must be >5 characters"
        }
        validDetails = isUserNameValid(username) &&
                isPasswordValid(password) && isNameValid(name)
        binding.nextButton.isEnabled = validDetails
    }

    private fun isNameValid(name: String): Boolean {
        // TODO
        return (name.isNotBlank())
    }

    private fun isUserNameValid(username: String): Boolean {
        // TODO
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        // TODO
        return password.length > 5
    }

    private fun isNewUser(username: String): Boolean {
        // TODO: Check if user is available
        return username.isNotEmpty()
    }

    private fun onboard() {
        // TODO: Get info from onboarding screens
        val intent = Intent(this, OnboardingActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun signUp(name: String, username: String, password: String) {
        try {
            // TODO: handle sign up authentication
            mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

            val user = User(0, name, username, password)
            mUserViewModel.addUser(user)

            val fakeUser = LoggedInUser(username, name)

            // Save user in SharedPreferences
            val prefs: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("user", fakeUser.displayName)
            editor.apply()

            updateUiWithUser(fakeUser)
            setResult(Activity.RESULT_OK)
        } catch (e: Throwable) {
            showSignUpFailed("Error signing up")
            setResult(Activity.RESULT_CANCELED)
        }
    }

    private fun updateUiWithUser(model: LoggedInUser) {
        // TODO
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        // Navigate to new page
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()

        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showSignUpFailed(errorString: String) {
        // TODO
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
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