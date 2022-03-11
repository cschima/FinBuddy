package com.example.fingrow.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fingrow.databinding.ActivityNewGoalBinding

class NewGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(binding.stageFragment.id, FirstNewGoalStageFragment())
            commit()
        }
    }
}