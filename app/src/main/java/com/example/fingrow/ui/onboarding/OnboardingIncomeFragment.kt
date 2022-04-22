package com.example.fingrow.ui.onboarding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ArrayAdapter
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.R
import com.example.fingrow.databinding.FragmentOnboardingIncomeBinding


class OnboardingIncomeFragment : Fragment() {
    object Constants {
        const val MY_POS = 3
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

        _binding = FragmentOnboardingIncomeBinding.inflate(inflater, container, false)

        val items = arrayOf("1", "2", "3", "4", "5", "6+")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.option_item, items)
        binding.autoCompleteTextView.setText("", false)
        binding.autoCompleteTextView.setAdapter(adapter)

        onboardingViewModel.setPos(Constants.MY_POS)
        onboardingViewModel.setValid(false)

        binding.slider.addOnChangeListener { _, value, _ ->
            when (value)  {
                binding.slider.valueFrom -> {
                    binding.incomeText.text = getString(R.string.low_or_less, value.toInt())
                }
                binding.slider.valueTo -> {
                    binding.incomeText.text = getString(R.string.high_or_more, value.toInt())
                }
                else -> {
                    binding.incomeText.text = getString(R.string.slider_value_string, value.toInt())
                }
            }
        }

        binding.autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                onboardingViewModel.setValid(true)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        val scrollView: ScrollView = binding.scrollView
        scrollView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                scrollView.viewTreeObserver
                    .removeOnGlobalLayoutListener(this)
                if (binding.linearLayout.height <= scrollView.height) {
                    scrollView.isVerticalScrollBarEnabled = false
                }
            }
        })

        return binding.root
    }
}