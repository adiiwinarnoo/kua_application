package com.example.kuaapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityLoginBinding
import com.example.kuaapplication.model.ResponseLogin
import com.example.kuaapplication.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    var answerCaptcha = 0
    var captchaOne = 0
    var captchaTwo = 0
    var email = ""
    var password = ""
    var apiConfig = ApiConfig()

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
            email = binding.edtUsername.text.toString()
            password = binding.edtPassword.text.toString()
            apiConfig.server.login(email, password).enqueue(object : Callback<ResponseLogin>{
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful){
                        if (response.body()?.data != null){
                            Constant.TOKEN_USER = response.body()!!.data?.accessToken!!
                            Constant.NAMA_LENGKAP = response.body()!!.data?.user?.name!!
                            Constant.JENIS_KELAMIN = response.body()!!.data?.user?.jenisKelamin!!
                            Constant.ALAMAT_USER = response.body()!!.data?.user?.alamat!!
                            Constant.NOMOR_HANDPHONE = response.body()!!.data?.user?.phone!!
                            Constant.USER_NAME = response.body()!!.data?.user?.username!!
                            Constant.EMAIL_USER = response.body()!!.data?.user?.email!!
                            Constant.ID_USER = response.body()!!.data?.user?.id!!
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Log.d("LOGIN-GAGAL", "onFailure: ${t.message}")
                }

            })
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