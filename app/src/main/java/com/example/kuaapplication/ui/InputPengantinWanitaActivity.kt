package com.example.kuaapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.kuaapplication.databinding.ActivityInputPengantinWanitaBinding
import com.example.kuaapplication.localDb.PetDao
import com.example.kuaapplication.localDb.RoomDatabaseItem
import com.example.kuaapplication.model.PengantinPria
import com.example.kuaapplication.model.PengantinWanita

class InputPengantinWanitaActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputPengantinWanitaBinding
    var namaWanita = ""
    var nomorKtpWanita = 0
    var tempatTanggalWanita = ""
    var alamatWanita = ""
    var statusWanita = 0
    lateinit var modelWanita : PengantinWanita
    lateinit var dbLocalWanita : RoomDatabaseItem
    lateinit var dbDao : PetDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPengantinWanitaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbLocalWanita = RoomDatabaseItem.getDatabase(this)
        dbDao = dbLocalWanita.dao()

        binding.btnSubmit.setOnClickListener {
            checktedData()
        }
        binding.spinnerStatusWanita.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0!!.selectedItem.equals("Janda")){
                    statusWanita = 3
                }else if (p0.selectedItem.equals("Perawan")){
                    statusWanita = 4
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    }

    private fun checktedData(){
        if (binding.edtNamaPria.text.isNullOrEmpty() || binding.edtNomorKtp.text.isNullOrEmpty() ||
            binding.edtTempatTanggalLahir.text.isNullOrEmpty() || binding.edtAlamat.text.isNullOrEmpty() ||
            statusWanita == 0){ Toast.makeText(this, "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
            namaWanita = binding.edtNamaPria.text.toString()
            val ktpPria = binding.edtNomorKtp.text
            nomorKtpWanita = ktpPria.toString().toInt()
            tempatTanggalWanita = binding.edtTempatTanggalLahir.text.toString()
            alamatWanita = binding.edtAlamat.text.toString()
            modelWanita = PengantinWanita(namaWanita,nomorKtpWanita,tempatTanggalWanita,alamatWanita,statusWanita)
            dbDao.insertWanita(com.example.kuaapplication.localDb.PengantinWanita(0,namaWanita,nomorKtpWanita,tempatTanggalWanita,alamatWanita,statusWanita))
            val intent = Intent(this,InputWaliActivity::class.java)
            startActivity(intent)
        }
    }
}