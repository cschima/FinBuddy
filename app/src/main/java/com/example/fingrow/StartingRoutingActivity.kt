package com.example.fingrow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.data.users.UserViewModel
import com.example.fingrow.ui.landing.LandingActivity
import kotlinx.coroutines.runBlocking

class StartingRoutingActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val timeT = Thread { Thread.sleep(1000) }
        val workT = Thread { doWork() }

        timeT.start()
        workT.start()
        timeT.join()
        workT.join()

        finish()
    }

    private fun doWork() {
        val userEmail = getSharedPreferences("login", MODE_PRIVATE).getString("email", "")
        if (userEmail != null && userEmail != "") {
            val user =
                runBlocking { userViewModel.findUser(userEmail.lowercase()) }

            if (user == null) {
                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)
            } else {
                SharedHelper(this).checkLastMonthUpdated(user)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }
    }
}