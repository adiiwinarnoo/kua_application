package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kuaapplication.databinding.ActivityHomeBinding
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menu1.setOnClickListener {
            Toast.makeText(this, "menu one", Toast.LENGTH_SHORT).show()
        }


    }
}