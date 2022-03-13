package com.example.fingrow.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.databinding.FragmentOnboardingIncomeBinding

class OnboardingIncomeFragment : Fragment() {

    object Constants {
        const val MY_POS = 2
    }

    private var _binding: FragmentOnboardingIncomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val onboardingViewModel =
            ViewModelProvider(requireActivity())[OnboardingViewModel::class.java]
        onboardingViewModel.setPos(Constants.MY_POS)

        _binding = FragmentOnboardingIncomeBinding.inflate(inflater, container, false)

//        binding.toggleGroup.addOnButtonCheckedListener { _, _, isChecked ->
//            if (isChecked) {
//                onboardingViewModel.setValid(true)
//            }
//        }

        return binding.root
    }
}