package com.example.fingrow.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.MainActivity
import com.example.fingrow.R
import com.example.fingrow.data.User
import com.example.fingrow.data.UserViewModel
import com.example.fingrow.data.model.LoggedInUserView
import com.example.fingrow.databinding.ActivitySignUpBinding
import com.example.fingrow.ui.login.LoginActivity
import com.example.fingrow.ui.onboarding.OnboardingActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mUserViewModel: UserViewModel

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                signUpViewModel.signUp(
                    this,
                    binding.name.text.toString(),
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.name
        val username = binding.username
        val password = binding.password
        val next = binding.nextButton

        signUpViewModel = ViewModelProvider(
            this,
            SignUpViewModelFactory()
        )[SignUpViewModel::class.java]

        signUpViewModel.signUpFormState.observe(this@SignUpActivity, Observer {
            val signUpState = it ?: return@Observer

            // disable next button unless name, username, and password is valid
            next.isEnabled = signUpState.isDataValid

            if (signUpState.nameError != null) {
                name.error = getString(signUpState.nameError)
            }
            if (signUpState.usernameError != null) {
                username.error = getString(signUpState.usernameError)
            }
            if (signUpState.passwordError != null) {
                password.error = getString(signUpState.passwordError)
            }
        })

        signUpViewModel.signUpResult.observe(this, Observer {
            val signUpResult = it ?: return@Observer

            if (signUpResult.error != null) {
                showSignUpFailed(signUpResult.error)
            }
            if (signUpResult.success != null) {
                updateUiWithUser(signUpResult.success)
            }

            setResult(Activity.RESULT_OK)
        })

        name.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                name.text.toString(),
                username.text.toString(),
                password.text.toString()
            )
        }

        username.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                name.text.toString(),
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                signUpViewModel.signUpDataChanged(
                    name.text.toString(),
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        onboard()
                    }
                }
                false
            }
        }

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        next.setOnClickListener {
            val user = User(0, name.text.toString(), username.text.toString(), password.text.toString())
            // add data to database
            mUserViewModel.addUser(user)
            onboard()
        }

        binding.logInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun onboard() {
        val intent = Intent(this, OnboardingActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
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

    private fun showSignUpFailed(@StringRes errorString: Int) {
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