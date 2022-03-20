package com.example.fingrow.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.MainActivity
import com.example.fingrow.SharedHelper
import com.example.fingrow.data.users.UserViewModel
import com.example.fingrow.databinding.ActivityLoginBinding
import com.example.fingrow.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedHelper: SharedHelper
    private lateinit var binding: ActivityLoginBinding

    private var isValidDetails: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sharedHelper = SharedHelper(this)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.email
        val password = binding.password

        email.afterTextChanged {
            loginDataChanged(
                email,
                password
            )
        }

        password.apply {
            afterTextChanged {
                loginDataChanged(
                    email,
                    password
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && isValidDetails) {
                    login(
                        email.text.toString(),
                        password.text.toString()
                    )
                }
                false
            }
        }

        binding.loginButton.setOnClickListener {
            login(
                email.text.toString(),
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

    private fun loginDataChanged(email: EditText, password: EditText) {
        val emailValid = sharedHelper.isEmailValid(email.text.toString())
        val passwordValid = sharedHelper.isPasswordValid(password.text.toString())
        if (email.text.isNotEmpty() && !emailValid) {
            email.error = "Not a valid email address"
        } else {
            email.error = null
        }
        if (password.text.isNotEmpty() && !passwordValid) {
            var errorString = ""
            for (err in sharedHelper.getPasswordErrors(password.text.toString())) {
                errorString += err + "\n"
            }
            password.error = errorString.trim()
        } else {
            password.error = null
        }

        isValidDetails = emailValid && passwordValid
        binding.loginButton.isEnabled = isValidDetails
    }

    private fun login(email: String, password: String) {
        try {
            userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
            val user = userViewModel.findUser(email)
            if (user == null || user.password != sharedHelper.hashPassword(password)) {
                binding.email.error = "Email/password combination not found"
                binding.password.error = "Email/password combination not found"
                showLoginFailed("Email/password combination not found")
                setResult(Activity.RESULT_CANCELED)
                return
            }

            // Save user in SharedPreferences
            val prefs: SharedPreferences = getSharedPreferences("login",
                MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("user", user.name)
            editor.apply()

            updateUiWithUser()
            setResult(Activity.RESULT_OK)
        } catch (e: Throwable) {
            showLoginFailed("Error logging in")
            setResult(Activity.RESULT_CANCELED)
        }
    }

    private fun updateUiWithUser() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
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