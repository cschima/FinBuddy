package com.example.fingrow.ui.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.databinding.ActivityNewGoalBinding

class NewGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newGoalViewModel =
            ViewModelProvider(this)[NewGoalViewModel::class.java]
        newGoalViewModel.setData(Intent())

        binding = ActivityNewGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(binding.stageFragment.id, NewGoalTitleAmountFragment())
            commit()
        }

        var actData: Intent? = null
        newGoalViewModel.data.observe(this, Observer {
            actData = it ?: return@Observer
        })

        newGoalViewModel.pos.observe(this, Observer {
            val pos = it ?: return@Observer

            binding.textStageOne.visibility = if (pos == 1) View.VISIBLE else View.GONE
            binding.checkedStageOne.visibility = if (pos == 1) View.GONE else View.VISIBLE
            binding.textStageTwo.alpha = if (pos == 1) 0.5f else 1f

            binding.nextStage.setOnClickListener {
                if (pos == 1) {
                    incrementStage()
                } else {
                    setResult(Activity.RESULT_OK, actData)
                    finish()
                }
            }
        })

        newGoalViewModel.valid.observe(this, Observer {
            val valid = it ?: return@Observer

            binding.nextStage.isEnabled = valid
        })
    }

    fun incrementStage() {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.stageFragment.id, NewGoalDateFragment())
            addToBackStack(null)
            commit()
        }
    }
}