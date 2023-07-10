package com.example.kuaapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kuaapplication.databinding.ActivityLoginBinding
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    var answerCaptcha = 0
    var captchaOne = 0
    var captchaTwo = 0
    var userName = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateCaptcha()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            checkedData()
        }

    }

    private fun checkedData() {
        val userAnswer = binding.edtCaptcha.text.toString().toIntOrNull()
        if (binding.edtUsername.text.isNullOrEmpty()) Toast.makeText(this, "username tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtPassword.text.isNullOrEmpty()) Toast.makeText(this, "password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtCaptcha.text.isNullOrEmpty()) Toast.makeText(this, "wajib mengisi captcha", Toast.LENGTH_SHORT).show()
        else if (userAnswer != answerCaptcha) Toast.makeText(this, "captcha tidak valid", Toast.LENGTH_SHORT).show()
        else{
            userName = binding.edtUsername.text.toString()
            password = binding.edtPassword.text.toString()
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    private fun generateCaptcha(){
        val randomInt = Random()
        captchaOne = randomInt.nextInt(10)
        captchaTwo = randomInt.nextInt(10)
        answerCaptcha = captchaOne + captchaTwo
        binding.tvCaptcha.setText("Berapakah hasil dari $captchaOne + $captchaTwo :")
        binding.edtCaptcha.clearFocus()
    }
}