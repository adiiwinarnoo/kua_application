package com.example.kuaapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kuaapplication.databinding.ActivityHomeBinding
import com.example.kuaapplication.utils.Constant
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menu1.setOnClickListener {
            startActivity(Intent(this,InputPengantinPriaActivity::class.java))
        }
        binding.menu2.setOnClickListener {
            startActivity(Intent(this, PembayaranActivity::class.java))
        }
        binding.menu3.setOnClickListener {
            startActivity(Intent(this,JadwalPelatihanActivity::class.java))
        }
        binding.menu4.setOnClickListener {
            startActivity(Intent(this,JadwalNikahActivity::class.java))
        }
        binding.menu5.setOnClickListener {
            startActivity(Intent(this,ProfilActivity::class.java))
        }

        binding.tvTittle.setText("Selamat Datang, ${Constant.USER_NAME}")


    }
}