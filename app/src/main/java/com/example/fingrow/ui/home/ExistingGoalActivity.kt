package com.example.fingrow.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import com.example.fingrow.R
import com.example.fingrow.databinding.ActivityExistingGoalBinding

class ExistingGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExistingGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("thisismine", "hey there")

        binding = ActivityExistingGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBack = binding.progressBack
        val progress = binding.progress
        progress.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                progressBack.viewTreeObserver
                    .removeOnGlobalLayoutListener(this)
                progressBack.indicatorSize = progressBack.width
                progress.indicatorSize = progress.width
            }
        })

        val extras = intent.extras
        if (extras != null) {
            binding.goalTitle.text = extras.getString("title")
            binding.currentAmount.text =
                getString(R.string.dollar_value, extras.getString("current_amount"))
            binding.totalAmount.text =
                getString(R.string.dollar_value, extras.getString("total_amount"))
            binding.progressPercentageTextView.text = extras.getString("progress") + "%"
            progress.progress = extras.getString("progress")!!.toInt() / 2

            val savings = extras.getString("savings_needed")
            if (savings == "") {
                binding.endDateTextView.text = getString(R.string.flexible)
                binding.info.text = getString(R.string.this_is_a_keep_up)
            } else {
                binding.endDateTextView.text = extras.getString("end_date")
                binding.info.text = getString(
                    R.string.based_on_your_current_progress,
                    extras.getString("savings_needed")
                )
            }
        }
    }
}