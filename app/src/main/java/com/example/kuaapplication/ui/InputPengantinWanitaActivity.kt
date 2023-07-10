package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.databinding.ActivityInputPengantinWanitaBinding

class InputPengantinWanitaActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputPengantinWanitaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPengantinWanitaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}