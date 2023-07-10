package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.databinding.ActivityInputWaliBinding

class InputWaliActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputWaliBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputWaliBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}