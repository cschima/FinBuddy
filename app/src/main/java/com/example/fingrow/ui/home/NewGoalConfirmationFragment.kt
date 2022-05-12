package com.example.fingrow.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.R
import com.example.fingrow.databinding.FragmentNewGoalConfirmationBinding
import java.text.SimpleDateFormat
import java.util.*

class NewGoalConfirmationFragment : Fragment() {

    private var _binding: FragmentNewGoalConfirmationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val newGoalViewModel =
            ViewModelProvider(requireActivity())[NewGoalViewModel::class.java]

        _binding = FragmentNewGoalConfirmationBinding.inflate(inflater, container, false)

        newGoalViewModel.setPos(3)
        newGoalViewModel.setValid(true)

        var actData: Intent? = null
        newGoalViewModel.data.observe(requireActivity(), Observer {
            actData = it ?: return@Observer
        })

        binding.titleTextView.text = actData!!.getStringExtra("title")
        binding.amountTextView.text = String.format("$%,.0f", actData!!.getStringExtra("amount")!!.toDouble())

        val month = actData!!.getStringExtra("month")!!.toInt()
        val year = actData!!.getStringExtra("year")!!.toInt()

        if (month == -1 || year == -1) {
            binding.endDateTextView.text = getString(R.string.flexible)
        } else {
            val c = Calendar.getInstance()
            c.set(year, month - 1, 1)
            binding.endDateTextView.text = SimpleDateFormat.getDateInstance().format(c.time)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}