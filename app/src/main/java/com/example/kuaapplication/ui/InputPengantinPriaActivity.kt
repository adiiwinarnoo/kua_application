package com.example.kuaapplication.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kuaapplication.databinding.ActivityInputPengantinPriaBinding
import com.example.kuaapplication.localDb.DatabasePermohonan
import com.example.kuaapplication.localDb.PetDao
import com.example.kuaapplication.localDb.RoomDatabaseItem
import com.example.kuaapplication.model.PengantinPria
import java.text.SimpleDateFormat
import java.util.*


class InputPengantinPriaActivity : AppCompatActivity() {

    lateinit var binding : ActivityInputPengantinPriaBinding
    val myCalendar = Calendar.getInstance()
    var jamNikah = ""
    var lokasiNikah = 0
    var tanggalNikah = ""
    var namaPria = ""
    var nomorKtpPria = 0
    var tempatTanggalPria = ""
    var alamatPria = ""
    var statusPria = 0
    lateinit var modelPria : PengantinPria
    lateinit var dbLocalPria : RoomDatabaseItem
    lateinit var dbDao : PetDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPengantinPriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbLocalPria = RoomDatabaseItem.getDatabase(this)
        dbDao = dbLocalPria.dao()

        val date = OnDateSetListener { datePicker, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                val format = "YYYY-MM-dd"
                val dateFormat = SimpleDateFormat(format, Locale.US)
                tanggalNikah = dateFormat.format(myCalendar.time)
                binding.edtTanggalNikah.setText(dateFormat.format(myCalendar.getTime()))
            }
        binding.spinnerLokasiNikah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                lokasiNikah = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinnerStatusPria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0!!.selectedItem.equals("Perjaka")){
                    statusPria = 1
                }else if (p0.selectedItem.equals("Duda")){
                    statusPria = 2
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.edtTanggalNikah.setOnClickListener {
            DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.edtJamNikah.setOnClickListener {
            showTimePicker()
        }
        binding.btnSubmit.setOnClickListener {
            checktedData()
        }
    }

    private fun showTimePicker() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(this, { timePicker, selectedHour, selectedMinute ->
            val jam = String.format("%02d", selectedHour)
            val menit = String.format("%02d", selectedMinute)
            binding.edtJamNikah.setText("$jam : $menit")
            jamNikah = "$jam:$menit"
            Log.d("PAYLOAD-INPUT-PERMOHONAN", "showTimePicker: $jamNikah")
        }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun checktedData(){
        if (binding.edtTanggalNikah.text.toString().isNullOrEmpty() || binding.edtJamNikah.text.toString().isNullOrEmpty() ||
            lokasiNikah == 0 || binding.edtNamaPria.text.isNullOrEmpty() || binding.edtNomorKtp.text.isNullOrEmpty() ||
            binding.edtTempatTanggalLahir.text.isNullOrEmpty() || binding.edtAlamat.text.isNullOrEmpty() ||
            statusPria == 0){
            Toast.makeText(this, "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
            namaPria = binding.edtNamaPria.text.toString()
            val ktpPria = binding.edtNomorKtp.text
            nomorKtpPria = ktpPria.toString().toInt()
            tempatTanggalPria = binding.edtTempatTanggalLahir.text.toString()
            alamatPria = binding.edtAlamat.text.toString()
            modelPria = PengantinPria(tanggalNikah = tanggalNikah, jamNikah = jamNikah, lokasiNikah = lokasiNikah,
                namaPria = namaPria, nomorKtpPria = nomorKtpPria, tempatTanggalLahir = tempatTanggalPria,
                alamatPria = alamatPria, statusPria = statusPria)
            val intent = Intent(this,InputPengantinWanitaActivity::class.java)
            dbDao.insert(DatabasePermohonan(0,tanggalNikah, jamNikah, lokasiNikah, namaPria,nomorKtpPria,tempatTanggalPria,alamatPria,statusPria))
            val dataGet = dbDao.getAllDataPria()
            Log.d("DATA-PRIA-LOCAL", "checktedData: $dataGet")
            startActivity(intent)
        }
    }
}