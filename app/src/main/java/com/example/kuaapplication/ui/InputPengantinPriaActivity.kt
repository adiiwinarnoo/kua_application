package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.databinding.ActivityInputPengantinPriaBinding

class InputPengantinPriaActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputPengantinPriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPengantinPriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}