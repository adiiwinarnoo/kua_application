package com.example.kuaapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityRegisterBinding
import com.example.kuaapplication.model.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    var captchaOne = 0
    var captchaTwo = 0
    var answerCaptcha = 0
    var userName = ""
    var password = ""
    var namaLengkap = ""
    var nomorHp = ""
    var alamat = ""
    var jenisKelamin = ""
    val apiConfig = ApiConfig()
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateCaptcha()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.btnSubmit.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
            checkedData()
        }

        binding.spinnerJenisKelamin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var selectedItem = p0?.getItemAtPosition(p2).toString()
                jenisKelamin = selectedItem
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    }

    private fun checkedData() {
        binding.progressbar.visibility = View.GONE
        val userAnswer = binding.edtCaptcha.text.toString().toIntOrNull()
        if (binding.edtUsername.text.isNullOrEmpty()) Toast.makeText(this, "username tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtPassword.text.isNullOrEmpty()) Toast.makeText(this, "password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtCaptcha.text.isNullOrEmpty()) Toast.makeText(this, "wajib mengisi captcha", Toast.LENGTH_SHORT).show()
        else if (binding.edtNamaLengkap.text.isNullOrEmpty()) Toast.makeText(this, "nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtNomorHp.text.isNullOrEmpty()) Toast.makeText(this, "nomor hp tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtAlamat.text.isNullOrEmpty()) Toast.makeText(this, "alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (binding.edtEmail.text.isNullOrEmpty()) Toast.makeText(this, "email tidak boleh kosong", Toast.LENGTH_SHORT).show()
        else if (userAnswer != answerCaptcha) Toast.makeText(this, "captcha tidak valid", Toast.LENGTH_SHORT).show()
        else{
            namaLengkap = binding.edtNamaLengkap.text.toString()
            nomorHp = binding.edtNomorHp.text.toString()
            alamat = binding.edtAlamat.text.toString()
            userName = binding.edtUsername.text.toString()
            password = binding.edtPassword.text.toString()
            email = binding.edtEmail.text.toString()
            apiConfig.server.register(namaLengkap,userName,email,nomorHp,password,alamat,jenisKelamin)
                .enqueue(object : Callback<ResponseLogin>{
                    override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                        if (response.isSuccessful){
                            if (response.body()?.data != null){
                                Toast.makeText(this@RegisterActivity, "register berhasil", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                            }
                        }
                    }
                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Log.d("REGISTER-FAILED", "onFailure: ${t.message}")
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