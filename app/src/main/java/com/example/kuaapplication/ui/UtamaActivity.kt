package com.example.kuaapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuaapplication.databinding.ActivityUtamaBinding

class UtamaActivity : AppCompatActivity() {

    lateinit var binding : ActivityUtamaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUtamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, InputPengantinPriaActivity::class.java))
        }


    }
}