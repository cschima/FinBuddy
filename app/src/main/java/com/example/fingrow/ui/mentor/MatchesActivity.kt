package com.example.fingrow.ui.mentor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fingrow.MainActivity
import com.example.fingrow.databinding.ActivityMatchesBinding

class MatchesActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nextButton.setOnClickListener {
            val intent = Intent(this, SelectMentorActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.skipButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}