package com.example.fingrow.ui.mentor.shelia

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.fingrow.MainActivity
import com.example.fingrow.databinding.FragmentSheilaBinding
import com.example.fingrow.ui.mentor.caroline.CarolineAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class SheilaFragment : Fragment() {
    private var _binding: FragmentSheilaBinding? = null

    private val binding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: CarolineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSheilaBinding.inflate(inflater, container, false)

        binding.interestButton.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager
        adapter = CarolineAdapter(parentFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2){tab,position ->
            when(position) {
                0 -> {
                    tab.text = "Experience"

                }
                1 -> {
                    tab.text = "Reviews"
                }

            }
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}