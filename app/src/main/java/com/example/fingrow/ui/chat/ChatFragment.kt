package com.example.fingrow.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fingrow.R
import com.example.fingrow.databinding.FragmentChatBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.darrenCard.setOnClickListener {

            val intent = Intent(activity, DarrenChatActivity::class.java)
            startActivity(intent)

            binding.numMessage.visibility = View.INVISIBLE

            val navBar: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            navBar.getOrCreateBadge(R.id.navigation_chat).apply {
                isVisible = false
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
