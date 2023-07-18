package com.example.kuaapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kuaapplication.R
import com.example.kuaapplication.model.DataJadwalPelatihan

class JadwalPelatihanAdapter (val models : ArrayList<DataJadwalPelatihan>) :
    RecyclerView.Adapter<JadwalPelatihanAdapter.ViewHolder>() {

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)  {
        var number = itemView.findViewById<TextView>(R.id.value_nomor)
        var tanggalPelatihan = itemView.findViewById<TextView>(R.id.value_tanggal)
        var jamPelatihan = itemView.findViewById<TextView>(R.id.value_jam)
        var lokasiPelatihan = itemView.findViewById<TextView>(R.id.value_lokasi)
        var pelatih = itemView.findViewById<TextView>(R.id.value_pelatih)
        var namaPria = itemView.findViewById<TextView>(R.id.value_nama_pria)
        var namaWanita = itemView.findViewById<TextView>(R.id.value_nama_wanita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal_pelatihan, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = 0
        holder.number.text = (position + 1).toString()
        holder.tanggalPelatihan.text = models[position].tanggal
        holder.jamPelatihan.text = models[position].jam
        if (models[position].tempatPelatih == null){
            holder.lokasiPelatihan.text = "null"
        }else{
            holder.lokasiPelatihan.text = models[position].tempatPelatih.toString()
        }
        holder.pelatih.text = models[position].namaPelatih
        holder.namaPria.text = models[position].namaPelatih
        holder.namaWanita.text = models[position].namaPelatih
    }

    override fun getItemCount(): Int {
        return models.size
    }
}