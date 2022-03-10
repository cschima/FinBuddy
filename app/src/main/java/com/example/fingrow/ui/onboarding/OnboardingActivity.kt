package com.example.fingrow.ui.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.R
import com.example.fingrow.databinding.ActivityOnboardingBinding
import com.example.fingrow.ui.signup.SignUpViewModel
import com.example.fingrow.ui.signup.SignUpViewModelFactory


class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingViewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

        val fragments: ArrayList<Fragment> = arrayListOf(
            FirstOnboardingFragment(),
//            SecondOnboardingFragment(),
            ThirdOnboardingFragment(),
            FourthOnboardingFragment()
        )

        binding.progress.max = fragments.size

        onboardingViewModel.pos.observe(this, Observer {
            val pos = it ?: return@Observer

            binding.progress.progress = pos

            binding.stepHeading.text = getString(
                R.string.step_heading, pos, fragments.size
            )

            binding.skipButton.setOnClickListener {
                if (pos < fragments.size) {
                    swapFrag(fragments[pos])
                }
                else {
                    setResult(RESULT_OK)
                }
            }

            binding.nextButton.setOnClickListener {
                if (pos < fragments.size) {
                    swapFrag(fragments[pos])
                }
                else {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        })

        onboardingViewModel.valid.observe(this, Observer {
            val valid = it ?: return@Observer

            binding.nextButton.isEnabled = valid
        })

        supportFragmentManager.beginTransaction().apply {
            replace(binding.onboardingFragment.id, fragments[0])
            commit()
        }
    }

    private fun swapFrag(frag: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.onboardingFragment.id, frag)
            addToBackStack(null);
            commit()
        }
    }
}