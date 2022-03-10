package com.example.fingrow.ui.meetings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.databinding.FragmentMeetingsBinding

class MeetingsFragment : Fragment() {

    private var _binding: FragmentMeetingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val meetingsViewModel =
            ViewModelProvider(this)[MeetingsViewModel::class.java]

        _binding = FragmentMeetingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMeetings
        meetingsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}