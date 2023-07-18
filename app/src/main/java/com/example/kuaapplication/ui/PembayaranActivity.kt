package com.example.kuaapplication.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityPembayaranBinding
import com.example.kuaapplication.model.ResponsePayment
import com.example.kuaapplication.utils.Constant
import com.example.kuaapplication.utils.RealPath
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PembayaranActivity : AppCompatActivity() {

    lateinit var binding : ActivityPembayaranBinding
    val myCalendar = Calendar.getInstance()
    var tanggalPembayaran = ""
    var uploadPembayaranBukti = ""
    val apiConfig = ApiConfig()
    var REQUEST_FOTO_PRIA = 100
    private lateinit var selectedPdfUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //date
        val date = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            val format = "YYYY-MM-dd"
            val dateFormat = SimpleDateFormat(format, Locale.US)
            tanggalPembayaran = dateFormat.format(myCalendar.time)
            binding.edtTanggalBayar.setText(dateFormat.format(myCalendar.getTime()))
        }

        binding.tvPilihFile.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_FOTO_PRIA)
        }

        binding.edtTanggalBayar.setOnClickListener {
            DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnAdd.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
            checkedData()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FOTO_PRIA){
            selectedPdfUri = data?.data!!
            uploadPembayaranBukti = RealPath.getRealPath(this,selectedPdfUri)
            binding.tvAfterPembayaran.setText(uploadPembayaranBukti)
        }
    }

    private fun checkedData(){
        if (binding.edtTanggalBayar.text.isNullOrEmpty() || binding.edtNominal.text.isNullOrEmpty()
            || uploadPembayaranBukti.equals("") || uploadPembayaranBukti.isNullOrEmpty()){
            binding.progressbar.visibility = View.GONE
            Toast.makeText(this, "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
            val uploadFile = File(uploadPembayaranBukti)
            val uploadBukti = uploadFile.asRequestBody("image/*".toMediaTypeOrNull())
            val uploadPart = MultipartBody.Part.createFormData("url_file",uploadFile.name,uploadBukti)

            val tanggalBayar = RequestBody.create(MultipartBody.FORM, tanggalPembayaran)
            var nominalBayar = RequestBody.create(MultipartBody.FORM, binding.edtNominal.text.toString())
            var idUser = RequestBody.create(MultipartBody.FORM, Constant.ID_USER.toString())

            apiConfig.server.uploadPayment(idUser,uploadPart,nominalBayar,tanggalBayar).enqueue(object : retrofit2.Callback<ResponsePayment>{
                override fun onResponse(
                    call: Call<ResponsePayment>, response: Response<ResponsePayment>) {
                    Log.d("UPLOAD-PAYMENT", "onResponse: ${response.code()}, ${response.message()}")
                    if (response.isSuccessful){
                        binding.progressbar.visibility = View.GONE
                        if (response.body()?.data != null){
                            Toast.makeText(this@PembayaranActivity, "upload pembayaran berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@PembayaranActivity,HomeActivity::class.java))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                    Log.d("UPLOAD-PAYMENT", "onFailure: ${t.message}")
                }

            })
        }
    }
}