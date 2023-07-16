package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuaapplication.R
import com.example.kuaapplication.databinding.ActivityProfilBinding
import com.example.kuaapplication.utils.Constant

class ProfilActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtNamaLengkap.setText(Constant.NAMA_LENGKAP)
        binding.edtAlamat.setText(Constant.ALAMAT_USER)
        binding.edtNomorHp.setText(Constant.NOMOR_HANDPHONE)
        binding.edtUsername.setText(Constant.USER_NAME)
        binding.edtEmail.setText(Constant.EMAIL_USER)

        if (Constant.JENIS_KELAMIN.equals("Laki-laki", ignoreCase = true)) {
            val jenisKelaminArray = resources.getStringArray(R.array.jenis_kelamin)
            for (i in jenisKelaminArray.indices) {
                if (jenisKelaminArray[i].equals("Laki-laki", ignoreCase = true)) {
                    binding.spinnerJenisKelamin.setSelection(i)
                    break
                }else if (jenisKelaminArray[i].equals("Perempuan", ignoreCase = true)){
                    binding.spinnerJenisKelamin.setSelection(i)
                    break
                }
            }
        }
    }
}