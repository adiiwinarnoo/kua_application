package com.example.kuaapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityInputWaliBinding
import com.example.kuaapplication.localDb.DatabasePermohonan
import com.example.kuaapplication.localDb.PengantinWanita
import com.example.kuaapplication.localDb.PetDao
import com.example.kuaapplication.localDb.RoomDatabaseItem
import com.example.kuaapplication.model.ResponseAddPemohon
import com.example.kuaapplication.model.Wali
import com.example.kuaapplication.utils.Helper
import com.example.kuaapplication.utils.RealPath
import com.example.kuaapplication.utils.UploadRequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class InputWaliActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {

    lateinit var binding : ActivityInputWaliBinding
    private lateinit var selectedPdfUri: Uri
    lateinit var modelWali : Wali
    var REQUEST_FOTO_PRIA = 1000
    var REQUEST_FOTO_WANITA = 2000
    var PICK_PDF_REQUEST_CODE = 3000
    var fotoPria = ""
    var fotoWanita = ""
    var berkasPdf = ""
    lateinit var dbLocalWali : RoomDatabaseItem
    lateinit var dbDao : PetDao
    lateinit var file : File
    val namaSaksi = "test"
    val ttlSaksi = "1999-04-04"
    val alamatSaksi = "rajeg"

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
    var masKawin = ""
    var namaWali = ""
    var alamatWali = ""
    var hubunganWali = ""
    lateinit var dataPria : List<DatabasePermohonan>
    lateinit var dataWanita : List<PengantinWanita>
    lateinit var dataWali : List<com.example.kuaapplication.localDb.Wali>
    lateinit var dataSaksi : List<com.example.kuaapplication.localDb.Saksi>
    lateinit var fileUri : File
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>




    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true &&
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                openPdfPicker()
            } else {
                Toast.makeText(
                    this,
                    "Izin akses penyimpanan ditolak ya.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputWaliBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbLocalWali = RoomDatabaseItem.getDatabase(this)
        dbDao = dbLocalWali.dao()
        dbDao.deleteDataWali()

//        initGallery()

        binding.tvPilihFile.setOnClickListener {
//           startGallery()
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_FOTO_PRIA)
        }
        binding.tvPilihFileWanita.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), REQUEST_FOTO_WANITA)
        }

        binding.tvBerkas.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    )
                )
            }
        }
        binding.btnSubmit.setOnClickListener {
            checkedData()
        }

    }

    private fun checkedData(){
        if (binding.edtMasKawin.text.isNullOrEmpty() || binding.edtNamaWali.text.isNullOrEmpty() ||
                binding.edtAlamatWali.text.isNullOrEmpty() || binding.edtHubungan.text.isNullOrEmpty() ||
                fotoPria.isNullOrEmpty() || fotoWanita.isNullOrEmpty() || berkasPdf.isNullOrEmpty() ){
            Toast.makeText(this, "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
            val masKawin = binding.edtMasKawin.text.toString()
            val namaWali = binding.edtNamaWali.text.toString()
            val alamatWali = binding.edtAlamatWali.text.toString()
            val hubunganWali = binding.edtHubungan.text.toString()
            modelWali = Wali(fotoPria,fotoWanita,berkasPdf,masKawin,namaWali,alamatWali,hubunganWali)
            dbDao.insertWali(com.example.kuaapplication.localDb.Wali(0,fotoPria,fotoWanita,berkasPdf,masKawin,namaWali,alamatWali,hubunganWali))
            val intent = Intent(this,InputSaksiActivity::class.java)
            intent.putExtra("fileUri", Uri.fromFile(file))
            startActivity(intent)
//            upload()
        }
    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri,null,null,null,null)
        if (returnCursor != null){
            var nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            PICK_PDF_REQUEST_CODE -> {
                selectedPdfUri = data?.data!!
                berkasPdf = selectedPdfUri.toString()
                binding.edtBerkas.setText(berkasPdf)
                uriToFile(selectedPdfUri)
            }
            REQUEST_FOTO_PRIA -> {
                selectedPdfUri = data?.data!!
                fotoPria = RealPath.getRealPath(this,selectedPdfUri)
                Log.d("PDF-FILE", "onActivityResult: $selectedPdfUri")
                binding.edtFotoPria.setText(fotoPria)
            }
            REQUEST_FOTO_WANITA -> {
                selectedPdfUri = data?.data!!
                fotoWanita = RealPath.getRealPath(this,selectedPdfUri)
                binding.edtFotoWanita.setText(fotoWanita)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PICK_PDF_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, lanjutkan dengan mengambil file PDF
                openPdfPicker()
            } else {
                // Izin tidak diberikan, tangani secara sesuai
                Toast.makeText(this, "Izin akses penyimpanan ditolak.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openPdfPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_REQUEST_CODE)
    }

    private fun uriToFile(uri: Uri): File? {
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        file = File(cacheDir, "temp.pdf")
        FileOutputStream(file).use { outputStream ->
            val buffer = ByteArray(4 * 1024) // Buffer size
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
        }
        return file
    }


    private fun upload(){
        dataPria = dbDao.getAllDataPria()
        dataWali = dbDao.getAllDataWali()
        dataWanita = dbDao.getAllDataWanita()
        dataSaksi = dbDao.getAllDataSaksi()

//        val filesDir = applicationContext.filesDir
//        val fileNew = File(filesDir,"image/jpg")
//        val inputStree = contentResolver.openInputStream(selectedPdfUri)
//        val outputStree = FileOutputStream(fileNew)
//        inputStree!!.copyTo(outputStree)
        Log.d("ADD-PERMOHON", "onResponse:  data pria ${dataPria.toString()},  data wanita${dataWanita.toString()}, data saksi${dataWali.toString()}")

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


        val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedPdfUri!!,"r",null)?:return
        val inputStreaaam = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val fileD = File(cacheDir,contentResolver.getFileName(selectedPdfUri))
        val outputStream = FileOutputStream(fileD)
        inputStreaaam.copyTo(outputStream)

        var fotoFile = UploadRequestBody(fileD,"image",this)


        val fileFotoPria = File(fotoPria)
//        val fotoFilePria = Helper.reduceFileImage(fileFotoPria)
        val fileFotoWanita= File(fotoWanita)
        val fileBerkas = file

        // Create request body for each part
        val fotoPriaImage = fileFotoPria.asRequestBody("image/*".toMediaTypeOrNull())
        val fotoWanitaImage = fileFotoWanita.asRequestBody("image/*".toMediaTypeOrNull())
        val pdfFile = fileBerkas.asRequestBody("application/pdf".toMediaTypeOrNull())

// Create MultipartBody.Part for each part
        val fotoPriaPart = MultipartBody.Part.createFormData("foto_pria", fileFotoPria.name, fotoPriaImage)
        val fotoWanitaPart = MultipartBody.Part.createFormData("foto_wanita", fileFotoWanita.name, fotoWanitaImage)
        val pdfPart = MultipartBody.Part.createFormData("berkas_persyaratan", fileBerkas.name, pdfFile)


        val tglNikah : RequestBody = RequestBody.create(MultipartBody.FORM, tanggalNikah)
        val jmNikah :  RequestBody = RequestBody.create(MultipartBody.FORM, jamNikah)
        val lokNikah :  RequestBody = RequestBody.create(MultipartBody.FORM, lokasiNikah.toString())
        val nmPria :  RequestBody = RequestBody.create(MultipartBody.FORM, namaPria)
        val noKtpP :  RequestBody = RequestBody.create(MultipartBody.FORM, nomorKtpPria.toString())
        val ttlPria :  RequestBody = RequestBody.create(MultipartBody.FORM, tempatTanggalLahirPria)
        val alPria :  RequestBody = RequestBody.create(MultipartBody.FORM, alamatPria)
        val stPria :  RequestBody = RequestBody.create(MultipartBody.FORM, statusPria.toString())
        val nmWanita :  RequestBody = RequestBody.create(MultipartBody.FORM, namaWanita)
        Log.d("PAYLOAD-DATA", "upload-ktp wanita: $nomorKtpWanita")
        Log.d("PAYLOAD-DATA", "upload-ktp ttl pria: $tempatTanggalLahirPria")
        Log.d("PAYLOAD-DATA", "upload-ktp nama wali: $tempatTanggalLahirPria")
        val noKtpW :  RequestBody = RequestBody.create(MultipartBody.FORM, nomorKtpWanita.toString())
        val ttlWanita :  RequestBody = RequestBody.create(MultipartBody.FORM, tempatTanggalWanita)
        val alWanita :  RequestBody = RequestBody.create(MultipartBody.FORM, alamatWanita)
        val stWanita :  RequestBody = RequestBody.create(MultipartBody.FORM, statusWanita.toString())
        val nmWali :  RequestBody = RequestBody.create(MultipartBody.FORM, binding.edtNamaWali.text.toString())
        val hubWali :  RequestBody = RequestBody.create(MultipartBody.FORM, binding.edtHubungan.text.toString())
        val masKa :  RequestBody = RequestBody.create(MultipartBody.FORM, binding.edtMasKawin.text.toString())
        val nmSaksi :  RequestBody = RequestBody.create(MultipartBody.FORM, namaSaksi)
        val ttSaksi :  RequestBody = RequestBody.create(MultipartBody.FORM, ttlSaksi)
        val alSaksi :  RequestBody = RequestBody.create(MultipartBody.FORM, alamatSaksi)

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
                            Toast.makeText(this@InputWaliActivity, "berhasil menambahkan data permohonan", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@InputWaliActivity,HomeActivity::class.java))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseAddPemohon>, t: Throwable) {
                    Log.d("ADD-PERMOHONAN-GAGAL", "onFailure: ${t.message}")
                }

            })
    }

//    private fun createTempFile(): File? {
//        val imageName = System.currentTimeMillis().toString() + "_image."
//        val file = File(
//            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//            imageName
//        )
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
//        //        CompresImage.compresSizeGallery(this, bitmap, file, imageName);
//        val bitmapdata: ByteArray = byteArrayOutputStream.toByteArray()
//        try {
//            val fileOutputStream = FileOutputStream(file)
//            fileOutputStream.write(bitmapdata)
//            fileOutputStream.flush()
//            fileOutputStream.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return file
//    }

//    private val conT = registerForActivityResult(ActivityResultContracts.GetContent()){
//        selectedPdfUri = it!!
//    }
private fun initGallery() {
    openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            selectedPdfUri = result.data?.data as Uri
            val imageFile = copyImageToCache(selectedPdfUri)
            if (imageFile != null){
                fotoPria = imageFile.absolutePath
            }else{
                Toast.makeText(this, "image file null", Toast.LENGTH_SHORT).show()
            }
//            fotoPria = getRealPathFromURI(selectedPdfUri)!!
            binding.edtFotoPria.setText(fotoPria)
        }
    }
}

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        openGalleryLauncher.launch(chooser)
    }
    private fun getRealPathFromURI(uri: Uri): String? {
        var realPath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (it.moveToFirst()) {
                realPath = it.getString(columnIndex)
            }
            it.close()
        }
        return realPath
    }

    private fun copyImageToCache(uri: Uri): File? {
        val cacheDir = applicationContext.cacheDir
        val file = File(cacheDir, "temp_image.jpg")

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream?.let { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onProgressUpdate(percentage: Int) {

    }
}


