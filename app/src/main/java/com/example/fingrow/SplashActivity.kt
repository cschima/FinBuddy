package com.example.fingrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fingrow.ui.LandingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splasher)

        if (getSharedPreferences("login", MODE_PRIVATE).getString("user", "") != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }

        finish()
    }
}