package com.example.fingrow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.fingrow.ui.landing.LandingActivity

class StartingRoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{true}
//        val prefs = getSharedPreferences("login",
//            MODE_PRIVATE
//        )
//        val editor = prefs.edit()
//        editor.clear().commit()
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