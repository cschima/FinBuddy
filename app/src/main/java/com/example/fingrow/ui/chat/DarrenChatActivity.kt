package com.example.fingrow.ui.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fingrow.databinding.ActivityDarrenChatBinding

class DarrenChatActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDarrenChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDarrenChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendar.setOnClickListener{
            val url = "https://calendly.com/ungjus19/fingrow-one-on-one-meeting?month=2022-04"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }
}