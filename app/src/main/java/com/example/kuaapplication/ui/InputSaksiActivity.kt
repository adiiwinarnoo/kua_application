package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.databinding.ActivityInputSaksiBinding

class InputSaksiActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputSaksiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputSaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}