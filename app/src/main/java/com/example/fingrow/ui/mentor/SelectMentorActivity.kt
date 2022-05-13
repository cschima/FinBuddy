package com.example.fingrow.ui.mentor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.fingrow.MainActivity
import com.example.fingrow.R
import com.example.fingrow.databinding.ActivitySelectMentorBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SelectMentorActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySelectMentorBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectMentorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.skipButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2){tab,position ->
            when(position) {
                0 -> {
                    tab.text = "Shelia"
                }
                1 -> {
                    tab.text = "Caroline"
                }
                2 -> {
                    tab.text = "Darren"
                }
            }
        }.attach()


    }
}