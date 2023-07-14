package com.example.kuaapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kuaapplication.databinding.ActivityInputWaliBinding
import com.example.kuaapplication.localDb.PetDao
import com.example.kuaapplication.localDb.RoomDatabaseItem
import com.example.kuaapplication.model.Wali
import com.example.kuaapplication.utils.RealPath


class InputWaliActivity : AppCompatActivity() {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputWaliBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbLocalWali = RoomDatabaseItem.getDatabase(this)
        dbDao = dbLocalWali.dao()

        binding.tvPilihFile.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), REQUEST_FOTO_PRIA)
        }
        binding.tvPilihFileWanita.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), REQUEST_FOTO_WANITA)
        }

        binding.tvBerkas.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "application/pdf"
//            val pdfIntent = Intent(Intent.ACTION_CHOOSER)
//            pdfIntent.type = "application/pdf"
//            startActivityForResult(Intent.createChooser(pdfIntent,"Pilih File"), PICK_PDF_REQUEST_CODE)
//            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
//            startActivityForResult(pdfIntent, PICK_PDF_REQUEST_CODE)
//            val file = File(Environment.getExternalStorageDirectory().absolutePath + "/example.pdf")
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.setDataAndType(Uri.fromFile(file), "application/pdf")
//            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//            startActivity(intent)

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
            // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
            startActivityForResult(intent, PICK_PDF_REQUEST_CODE)
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
            startActivity(intent)
        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        var realPath = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                realPath = it.getString(columnIndex)
            }
        }
        return realPath
    }


    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            PICK_PDF_REQUEST_CODE -> {
                selectedPdfUri = data?.data!!
                berkasPdf = RealPath.getRealPath(this,selectedPdfUri)
                Log.d("PDF-FILE", "onActivityResult: $berkasPdf")
//                berkasPdf = getRealPathFromUri(selectedPdfUri)
                binding.edtBerkas.setText(berkasPdf)
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

//    private fun getFileName(uri: Uri): String {
//        var result = ""
//        val cursor = contentResolver.query(uri, null, null, null, null)
//        cursor?.use {
//            if (it.moveToFirst()) {
//                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                result = displayName ?: ""
//            }
//        }
//        return result
//    }
}