package com.example.fingrow.ui.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fingrow.R
import com.example.fingrow.databinding.FragmentChatBinding
import com.example.fingrow.ui.mentor.SelectMentorActivity

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

        binding.darrenCard.setOnClickListener {
            //Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        }

        val root: View = binding.root

//        val textView: TextView = binding.textChat
//        chatViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.darrenMeeting.setOnClickListener{
            val url = "https://calendly.com/ungjus19/fingrow-one-on-one-meeting?month=2022-04"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.sheliaMeeting.setOnClickListener{
//            val url = "http://www.google.com"
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(url)
//            startActivity(i)
            Toast.makeText(activity, "Meeting Link Not Available", Toast.LENGTH_SHORT).show();
        }

        binding.darrenCard.setOnClickListener {
            val intent = Intent(activity, DarrenChatActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
