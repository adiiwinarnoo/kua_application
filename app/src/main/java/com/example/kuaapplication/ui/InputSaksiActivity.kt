package com.example.kuaapplication.ui

import android.content.Intent
import android.net.Uri
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
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


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
    lateinit var fileUri : File
    lateinit var fileP : File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputSaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbLocalSaksi = RoomDatabaseItem.getDatabase(this)
        dbDao = dbLocalSaksi.dao()
        dbDao.deleteDataSaksi()

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

            val fileFotoPria = File(fotoPria)
            val fileFotoWanita= File(fotoWanita)
            val berkasUri = Uri.parse(berkasPdf)
            uriToFile(berkasUri)
//
//            var imageRequest = fileFotoPria.asRequestBody("image/jpg".toMediaTypeOrNull())
//            var imageRequestWanita = fileFotoPria.asRequestBody("image/jpg".toMediaTypeOrNull())
//            var pdfRequest = fileUri.asRequestBody("application/pdf".toMediaTypeOrNull())

//            var fotoPriaImage = MultipartBody.Part.createFormData("file", fileFotoPria.name, imageRequest)
//            var fotoWanitaImage = MultipartBody.Part.createFormData("file", fileFotoWanita.name, imageRequestWanita)
//            var pdfFile = MultipartBody.Part.createFormData("file", fileUri.name, pdfRequest)

            // Create request body for each part
            val fotoPriaImage = fileFotoPria.asRequestBody("image/*".toMediaTypeOrNull())
            val fotoWanitaImage = fileFotoWanita.asRequestBody("image/*".toMediaTypeOrNull())
            val pdfFile = fileP.asRequestBody("application/pdf".toMediaTypeOrNull())

            // Create MultipartBody.Part for each part
            val fotoPriaPart = MultipartBody.Part.createFormData("foto_pria", fileFotoPria.name, fotoPriaImage)
            val fotoWanitaPart = MultipartBody.Part.createFormData("foto_wanita", fileFotoWanita.name, fotoWanitaImage)
            val pdfPart = MultipartBody.Part.createFormData("berkas_persyaratan", fileP.name, pdfFile)


            val tglNikah : RequestBody = RequestBody.create(MultipartBody.FORM, tanggalNikah)
            val jmNikah : RequestBody = RequestBody.create(MultipartBody.FORM, jamNikah)
            val lokNikah : RequestBody = RequestBody.create(MultipartBody.FORM, lokasiNikah.toString())
            val nmPria : RequestBody = RequestBody.create(MultipartBody.FORM, namaPria)
            val noKtpP : RequestBody = RequestBody.create(MultipartBody.FORM, nomorKtpPria.toString())
            val ttlPria : RequestBody = RequestBody.create(MultipartBody.FORM, tempatTanggalLahirPria)
            val alPria : RequestBody = RequestBody.create(MultipartBody.FORM, alamatPria)
            val stPria : RequestBody = RequestBody.create(MultipartBody.FORM, statusPria.toString())
            val nmWanita : RequestBody = RequestBody.create(MultipartBody.FORM, namaWanita)
            val noKtpW : RequestBody = RequestBody.create(MultipartBody.FORM, nomorKtpWanita.toString())
            val ttlWanita : RequestBody = RequestBody.create(MultipartBody.FORM, tempatTanggalWanita)
            val alWanita : RequestBody = RequestBody.create(MultipartBody.FORM, alamatWanita)
            val stWanita : RequestBody = RequestBody.create(MultipartBody.FORM, statusWanita.toString())
            val nmWali : RequestBody = RequestBody.create(MultipartBody.FORM, namaWali)
            val hubWali : RequestBody = RequestBody.create(MultipartBody.FORM, hubunganWali)
            val masKa : RequestBody = RequestBody.create(MultipartBody.FORM, masKawin)
            val nmSaksi : RequestBody = RequestBody.create(MultipartBody.FORM, binding.edtSaksi.text.toString())
            val ttSaksi : RequestBody = RequestBody.create(MultipartBody.FORM, binding.edtTtl.text.toString())
            val alSaksi : RequestBody = RequestBody.create(MultipartBody.FORM, binding.edtAlamatSaksi.text.toString())

            apiConfig.server.addPermohonan(
                tglNikah,jmNikah,lokNikah,nmPria,noKtpP,ttlPria,alPria,
                stPria,nmWanita,noKtpW,ttlWanita,alWanita,stWanita,nmWali,
                hubWali,masKa,nmSaksi,ttSaksi,alSaksi,pdfPart,fotoWanitaPart,fotoPriaPart)
                .enqueue(object : Callback<ResponseAddPemohon> {
                    override fun onResponse(call: Call<ResponseAddPemohon>, response: Response<ResponseAddPemohon>) {
                        Log.d("ADD-PERMOHON", "onResponse: ${response.code()} ${response.message()}")
                        Log.d("ADD-PERMOHON", "onResponse-payload: " +
                                "tanggal nikah${tanggalNikah}, jam nikah ${jamNikah}, namaPria${namaPria}, nama wanita $namaWanita, no ktp pria $nomorKtpPria")
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

    private fun uriToFile(uri: Uri): File? {
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        fileP = File(cacheDir, "temp.pdf")
        FileOutputStream(fileP).use { outputStream ->
            val buffer = ByteArray(4 * 1024) // Buffer size
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
        }
        return fileP
    }


}