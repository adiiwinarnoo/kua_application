package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.databinding.ActivityJadwalPelatihanBinding

class JadwalPelatihanActivity : AppCompatActivity() {

    lateinit var binding : ActivityJadwalPelatihanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalPelatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}