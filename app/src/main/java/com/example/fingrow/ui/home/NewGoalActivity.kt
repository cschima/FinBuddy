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

            when (pos) {
                1 -> {
                    binding.textStageOne.visibility = View.VISIBLE
                    binding.checkedStageOne.visibility = View.GONE
                    binding.textStageTwo.visibility = View.VISIBLE
                    binding.checkedStageTwo.visibility = View.GONE
                    binding.textStageTwo.alpha = .5F
                }
                2 -> {
                    binding.textStageOne.visibility = View.GONE
                    binding.checkedStageOne.visibility = View.VISIBLE
                    binding.textStageTwo.visibility = View.VISIBLE
                    binding.checkedStageTwo.visibility = View.GONE
                    binding.textStageTwo.alpha = 1F
                }
                else -> {
                    binding.textStageOne.visibility = View.GONE
                    binding.checkedStageOne.visibility = View.VISIBLE
                    binding.textStageTwo.visibility = View.GONE
                    binding.checkedStageTwo.visibility = View.VISIBLE
                }
            }

            binding.nextStage.setOnClickListener {
                if (pos == 3) {
                    setResult(Activity.RESULT_OK, actData)
                    finish()
                } else {
                    incrementStage(pos)
                }
            }
        })

        newGoalViewModel.valid.observe(this, Observer {
            val valid = it ?: return@Observer

            binding.nextStage.isEnabled = valid
        })
    }

    fun incrementStage(pos: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.stageFragment.id, if (pos == 1) NewGoalDateFragment() else NewGoalConfirmationFragment())
            addToBackStack(null)
            commit()
        }
    }
}