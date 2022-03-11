package com.example.fingrow.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fingrow.databinding.ActivityLandingBinding
import com.example.fingrow.ui.login.LoginActivity
import com.example.fingrow.ui.signup.SignUpActivity
import com.google.android.material.tabs.TabLayoutMediator

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pager: ViewPager2 = binding.pager
        val fragments: ArrayList<Fragment> = arrayListOf(
            FirstLandingFragment(),
            SecondLandingFragment(),
            ThirdLandingFragment()
        )
        val adapter = ViewPagerAdapter(fragments, this)
        pager.adapter = adapter

        TabLayoutMediator(binding.tabDots, pager) { _, _ -> }.attach()

        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}