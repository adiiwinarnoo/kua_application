package com.example.kuaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuaapplication.adapter.JadwalPelatihanAdapter
import com.example.kuaapplication.api.ApiConfig
import com.example.kuaapplication.databinding.ActivityJadwalPelatihanBinding
import com.example.kuaapplication.model.DataJadwalPelatihan
import com.example.kuaapplication.model.ResponseGetJadwalPelatihan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalPelatihanActivity : AppCompatActivity() {

    lateinit var binding : ActivityJadwalPelatihanBinding
    lateinit var adapter : JadwalPelatihanAdapter
    val apiConfig = ApiConfig()
    var dataArrayList = ArrayList<DataJadwalPelatihan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalPelatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        apiConfig.server.getJadwalPelatihan().enqueue(object : Callback<ResponseGetJadwalPelatihan>{
            override fun onResponse(call: Call<ResponseGetJadwalPelatihan>, response: Response<ResponseGetJadwalPelatihan>) {
                Log.d("GET-JADWAL-PELATIHAN", "onresponse: ${response.message()}, ${response.code()}")
                if (response.isSuccessful){
                    if (response.body()?.data != null){
                        val data = response.body()!!.data
                        data.let {
                            dataArrayList.add(it!!)
                        }
                        Log.d("DATA-GET", "onResponse: ${dataArrayList.toString()}")
                        adapter = JadwalPelatihanAdapter(dataArrayList)
                        binding.recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<ResponseGetJadwalPelatihan>, t: Throwable) {
                Log.d("GET-JADWAL-PELATIHAN", "onFailure: ${t.message}")
            }

        })
    }
}