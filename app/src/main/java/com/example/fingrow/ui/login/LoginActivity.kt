package com.example.fingrow.ui.login

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
import androidx.appcompat.app.AppCompatActivity
import com.example.fingrow.MainActivity
import com.example.fingrow.R
import com.example.fingrow.data.model.LoggedInUser
import com.example.fingrow.databinding.ActivityLoginBinding
import com.example.fingrow.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.loginButton

        username.afterTextChanged {
            loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        login(
                            username.text.toString(),
                            password.text.toString()
                        )
                    }
                }
                false
            }
        }

        login.setOnClickListener {
            login(
                username.text.toString(),
                password.text.toString()
            )
        }

        binding.forgotPasswordButton.setOnClickListener {
            Toast.makeText(applicationContext,"Oops!", Toast.LENGTH_SHORT).show()
        }

        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginDataChanged(username: String, password: String) {
        // TODO
        if (!isUserNameValid(username)) {
            binding.username.error = "Not a valid username"
        }
        if (!isPasswordValid(password)) {
            binding.password.error = "Password must be >5 characters"
        }
        binding.loginButton.isEnabled = isUserNameValid(username) &&
                isPasswordValid(password)
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

    private fun login(username: String, password: String) {
        try {
            // TODO: handle login authentication
            val fakeUser = LoggedInUser(username, username)

            // Save user in SharedPreferences
            val prefs: SharedPreferences = getSharedPreferences("login",
                MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("user", fakeUser.displayName)
            editor.apply()

            updateUiWithUser(fakeUser)
            setResult(Activity.RESULT_OK)
        } catch (e: Throwable) {
            showLoginFailed("Error logging in")
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

    private fun showLoginFailed(errorString: String) {
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