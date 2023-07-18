package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuaapplication.adapter.JadwalNikahAdapter
import com.example.kuaapplication.adapter.JadwalPelatihanAdapter
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityJadwalNikahBinding
import com.example.kuaapplication.model.DataJadwalNikah
import com.example.kuaapplication.model.DataJadwalPelatihan
import com.example.kuaapplication.model.ResponseGetJadwalNikah
import com.example.kuaapplication.model.ResponseGetJadwalPelatihan
import com.example.kuaapplication.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalNikahActivity : AppCompatActivity() {

    lateinit var binding : ActivityJadwalNikahBinding
    lateinit var adapter : JadwalNikahAdapter
    val apiConfig = ApiConfig()
    var dataArrayList = ArrayList<DataJadwalNikah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalNikahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        apiConfig.server.getJadwalNikah().enqueue(object :
            Callback<ResponseGetJadwalNikah> {
            override fun onResponse(call: Call<ResponseGetJadwalNikah>, response: Response<ResponseGetJadwalNikah>) {
                Log.d("GET-JADWAL-PELATIHAN", "onresponse: ${response.message()}, ${response.code()}")
                if (response.isSuccessful){
                    if (response.body()?.data != null){
                        val data = response.body()!!.data
                        data.let {
                            dataArrayList.add(it!!)
                        }
                        Log.d("DATA-GET", "onResponse: ${response.body()!!.data.toString()}")
                        Log.d("DATA-GET", "onResponse: ${Constant.TOKEN_USER}")
                        adapter = JadwalNikahAdapter(dataArrayList)
                        binding.recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<ResponseGetJadwalNikah>, t: Throwable) {
                Log.d("GET-JADWAL-PELATIHAN", "onFailure: ${t.message}")
            }

        })
    }
}