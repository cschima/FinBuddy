package com.example.fingrow.ui.account

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fingrow.databinding.FragmentAccountBinding
import com.example.fingrow.ui.landing.LandingActivity
import com.example.fingrow.ui.mentor.SelectMentorActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        binding.userNameText.text = requireActivity().getSharedPreferences(
            "login",
            AppCompatActivity.MODE_PRIVATE
        )?.getString("name", "")

        binding.startDateText.text = requireActivity().getSharedPreferences(
            "login",
            AppCompatActivity.MODE_PRIVATE
        )?.getString("start_date", "")

        binding.logoutButton.setOnClickListener {
            val prefs: SharedPreferences = requireActivity().getSharedPreferences("login",
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.remove("name")
            editor.remove("email")
            editor.remove("start_date")
            editor.remove("this_month_saved")
            editor.remove("total_saved")
            editor.apply()

            val intent = Intent(activity, LandingActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.mentorsButton.setOnClickListener{
            val intent = Intent(activity, SelectMentorActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}