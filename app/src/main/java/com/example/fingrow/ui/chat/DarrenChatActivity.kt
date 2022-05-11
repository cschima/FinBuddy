package com.example.fingrow.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fingrow.databinding.ActivityDarrenChatBinding

class DarrenChatActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDarrenChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDarrenChatBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}