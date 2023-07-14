package com.example.kuaapplication.ui

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityInputSaksiBinding
import com.example.kuaapplication.localDb.DatabasePermohonan
import com.example.kuaapplication.localDb.PengantinWanita
import com.example.kuaapplication.localDb.PetDao
import com.example.kuaapplication.localDb.RoomDatabaseItem
import com.example.kuaapplication.localDb.Wali
import com.example.kuaapplication.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class InputSaksiActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputSaksiBinding
    lateinit var modelSaksi : Saksi
    var apiConfig = ApiConfig()
    var tanggalNikah = ""
    var jamNikah = ""
    var lokasiNikah = 0
    var namaPria = ""
    var nomorKtpPria = 0
    var tempatTanggalLahirPria = ""
    var alamatPria = ""
    var statusPria = 0
    var namaWanita = ""
    var nomorKtpWanita = 0
    var tempatTanggalWanita = ""
    var alamatWanita = ""
    var statusWanita = 0
    var fotoPria = ""
    var fotoWanita = ""
    var berkasPdf = ""
    var masKawin = ""
    var namaWali = ""
    var alamatWali = ""
    var hubunganWali = ""
    lateinit var dbLocalSaksi : RoomDatabaseItem
    lateinit var dbDao : PetDao
    lateinit var dataPria : List<DatabasePermohonan>
    lateinit var dataWanita : List<PengantinWanita>
    lateinit var dataWali : List<Wali>
    lateinit var dataSaksi : List<com.example.kuaapplication.localDb.Saksi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputSaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbLocalSaksi = RoomDatabaseItem.getDatabase(this)
        dbDao = dbLocalSaksi.dao()

        binding.btnSubmit.setOnClickListener {
            checkedData()
        }
    }

    private fun checkedData(){
        if (binding.edtSaksi.text.isNullOrEmpty() || binding.edtTtl.text.isNullOrEmpty() || binding.edtAlamatSaksi.text.isNullOrEmpty()){
            Toast.makeText(this, "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
            dataPria = dbDao.getAllDataPria()
            dataWali = dbDao.getAllDataWali()
            dataWanita = dbDao.getAllDataWanita()
            dataSaksi = dbDao.getAllDataSaksi()

            Log.d("DATA-DATA", "checkedData: data pria $dataPria")
            Log.d("DATA-DATA", "checkedData: data pria $dataWali")
            Log.d("DATA-DATA", "checkedData: data pria $dataWanita")
            Log.d("DATA-DATA", "checkedData: data pria $dataSaksi")

            for(i in dataPria){
                tanggalNikah = i.tanggalNikah
                jamNikah = i.jamNikah
                lokasiNikah = i.lokasiNikah
                namaPria = i.namaPria
                nomorKtpPria = i.noKtpPria
                tempatTanggalLahirPria = i.tempatTanggalLahirPria
                alamatPria = i.alamat
                statusPria = i.statusPria
            }
            for (i in dataWanita){
                namaWanita = i.namaWanita
                nomorKtpWanita = i.noKtpWanita
                tempatTanggalWanita = i.tempatTanggalLahirWanita
                alamatWanita = i.alamat
                statusWanita = i.statusWanita
            }
            for (i in dataWali){
                namaWali = i.namaWali
                hubunganWali = i.namaWali
                masKawin = i.masKawin
                fotoPria = i.fotoPria
                fotoWanita = i.fotoWanita
                berkasPdf = i.berkasFile
                alamatWali = i.alamatWali
            }

            val namaSaksi = binding.edtSaksi.text.toString()
            val tanggalLahir = binding.edtTtl.text.toString()
            val alamat = binding.edtAlamatSaksi.text.toString()
            modelSaksi = Saksi(namaSaksi,tanggalLahir,alamat)
            dbDao.insertSaksi(com.example.kuaapplication.localDb.Saksi(0,namaSaksi,tanggalLahir,alamat))
            Log.d("MODEL-", "checkedData-saksi: $modelSaksi")
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            Log.d("FOTO---", "checkedData: foto pria $fotoPria, foto wanita $fotoWanita, pdf $berkasPdf")
            val fileFotoPria = File(fotoPria)
            val fileFotoWanita= File(fotoWanita)
            Log.d("BERKAS-PDF", "checkedData: $berkasPdf")
            val fileBerkas = File(berkasPdf)
            var imageRequest = fileFotoPria.asRequestBody("image/jpg".toMediaTypeOrNull())
            var imageRequestWanita = fileFotoPria.asRequestBody("image/jpg".toMediaTypeOrNull())
            var pdfRequest = fileBerkas.asRequestBody("application/pdf".toMediaTypeOrNull())
            var fotoPriaImage = MultipartBody.Part.createFormData("file", fileFotoPria.name, imageRequest)
            var fotoWanitaImage = MultipartBody.Part.createFormData("file", fileFotoWanita.name, imageRequestWanita)
            var pdfFile = MultipartBody.Part.createFormData("file", fileBerkas.name, pdfRequest)

            Log.d("DATA-GAMBAR", "checkedData: pdf $pdfFile, foto wanita $fotoWanitaImage, foto pria $fotoPriaImage")
            apiConfig.server.addPermohonan(
                tanggalNikah,jamNikah,lokasiNikah,namaPria,nomorKtpPria,tempatTanggalLahirPria,alamatPria,
                statusPria,namaWanita,nomorKtpWanita,tempatTanggalWanita,alamatWanita,statusWanita,namaWali,
                hubunganWali,masKawin,namaSaksi,tanggalLahir,alamat,pdfFile,fotoWanitaImage,fotoPriaImage)
                .enqueue(object : Callback<ResponseAddPemohon>{
                    override fun onResponse(call: Call<ResponseAddPemohon>, response: Response<ResponseAddPemohon>) {
                        Log.d("ADD-PERMOHON", "onResponse: ${response.code()}, ${response.message()}")
                        if (response.isSuccessful){
                            if (response.body()?.status == true){
                                Toast.makeText(this@InputSaksiActivity, "berhasil menambahkan data permohonan", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@InputSaksiActivity,HomeActivity::class.java))
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseAddPemohon>, t: Throwable) {
                        Log.d("ADD-PERMOHONAN-GAGAL", "onFailure: ${t.message}")
                    }

                })
        }
    }
}