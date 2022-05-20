package com.example.fingrow.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.databinding.FragmentNewGoalDateBinding
import com.super_rabbit.wheel_picker.OnValueChangeListener
import com.super_rabbit.wheel_picker.WheelAdapter
import com.super_rabbit.wheel_picker.WheelPicker
import java.util.*

class NewGoalDateFragment : Fragment() {

    private var _binding: FragmentNewGoalDateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val months = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val newGoalViewModel =
            ViewModelProvider(requireActivity())[NewGoalViewModel::class.java]

        _binding = FragmentNewGoalDateBinding.inflate(inflater, container, false)

        newGoalViewModel.setPos(2)

        var actData: Intent? = null
        newGoalViewModel.data.observe(requireActivity(), Observer {
            actData = it ?: return@Observer
        })

        val mp = binding.monthPicker
        val yp = binding.yearPicker
        val year = Calendar.getInstance().get(Calendar.YEAR)

        // Set user defined adapter
        mp.setAdapter(MonthPickerAdapter())
        yp.setMinValue(year)
        yp.setMaxValue(year + 100)
        yp.setAdapter(YearPickerAdapter())

        newGoalViewModel.setValid(isValid())

        binding.flexibleButton.setOnClickListener {
            val moIndex = months.indexOf(mp.getCurrentItem())
            val yearIndex = yp.getCurrentItem().toInt()
            newGoalViewModel.setValid(isValid())
            if (binding.flexibleButton.isChecked) {
                mp.setMinValue(moIndex + 1)
                mp.setMaxValue(moIndex + 1)
                mp.setWrapSelectorWheel(false)
                mp.setSelectedTextColor(com.example.fingrow.R.color.dark_grey)

                yp.setMinValue(yearIndex)
                yp.setMaxValue(yearIndex)
                yp.setSelectedTextColor(com.example.fingrow.R.color.dark_grey)

                actData!!.putExtra("month", "-1")
                actData!!.putExtra("year", "-1")
                newGoalViewModel.setData(actData!!)
            } else {
                mp.setMinValue(1)
                mp.setMaxValue(12)
                mp.setWrapSelectorWheel(true)
                mp.setSelectedTextColor(com.example.fingrow.R.color.like_black)

                yp.setMinValue(year)
                yp.setMaxValue(year + 100)
                yp.setSelectedTextColor(com.example.fingrow.R.color.like_black)

                actData!!.putExtra("month", (moIndex + 1).toString())
                actData!!.putExtra("year", yearIndex.toString())
                newGoalViewModel.setData(actData!!)
            }
        }

        mp.setOnValueChangedListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                newGoalViewModel.setValid(isValid())
            }
        })

        yp.setOnValueChangedListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                newGoalViewModel.setValid(isValid())
            }
        })

        newGoalViewModel.valid.observe(requireActivity(), Observer {
            val valid = it ?: return@Observer

            if (valid) {
                val moIndex = if (binding.flexibleButton.isChecked) -1 else (months.indexOf(mp.getCurrentItem()) + 1)
                val yearIndex = if (binding.flexibleButton.isChecked) -1 else yp.getCurrentItem().toInt()
                actData!!.putExtra("month", (moIndex).toString())
                actData!!.putExtra("year", yearIndex.toString())
                newGoalViewModel.setData(actData!!)
            }
        })

        return binding.root
    }

    private fun isValid(): Boolean {
        return if (
            !binding.flexibleButton.isChecked &&
            binding.yearPicker.getCurrentItem() ==
            Calendar.getInstance().get(Calendar.YEAR).toString()
        ) {
            months.indexOf(binding.monthPicker.getCurrentItem()) >
                    Calendar.getInstance().get(Calendar.MONTH)
        } else true
    }
}

// Adapter sample
/**
 * Custom wheel picker adapter for implementing a date picker
 */
class MonthPickerAdapter : WheelAdapter() {
    //get item value based on item position in wheel
    override fun getValue(position: Int): String {
        return when (position) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> ""
        }
    }

    override fun getPosition(vale: String): Int {
        return when (vale) {
            "January" -> 1
            "February" -> 2
            "March" -> 3
            "April" -> 4
            "May" -> 5
            "June" -> 6
            "July" -> 7
            "August" -> 8
            "September" -> 9
            "October" -> 10
            "November" -> 11
            "December" -> 12
            else -> -1
        }
    }

    override fun getTextWithMaximumLength(): String {
        return "September"
    }
}

// Adapter sample
/**
 * Custom wheel picker adapter for implementing a date picker
 */
class YearPickerAdapter : WheelAdapter() {
    //get item value based on item position in wheel
    override fun getValue(position: Int): String {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        return if (position >= year && position <= year + 100) position.toString() else ""
    }

    override fun getPosition(vale: String): Int {
        val intRep = vale.toIntOrNull()
        return intRep ?: 0
    }

    override fun getTextWithMaximumLength(): String {
        return "0000"
    }
}