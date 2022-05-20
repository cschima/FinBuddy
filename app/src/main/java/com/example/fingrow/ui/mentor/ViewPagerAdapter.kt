package com.example.fingrow.ui.mentor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fingrow.ui.mentor.caroline.CarolineFragment
import com.example.fingrow.ui.mentor.darren.DarrenFragment
import com.example.fingrow.ui.mentor.shelia.SheilaFragment

class ViewPagerAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                SheilaFragment()
            }
            1 -> {
                CarolineFragment()
            }
            2 -> {
                DarrenFragment()
            }
            else -> {
                Fragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}