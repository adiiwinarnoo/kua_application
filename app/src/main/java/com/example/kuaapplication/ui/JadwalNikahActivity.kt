package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.databinding.ActivityJadwalNikahBinding

class JadwalNikahActivity : AppCompatActivity() {

    lateinit var binding : ActivityJadwalNikahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalNikahBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}