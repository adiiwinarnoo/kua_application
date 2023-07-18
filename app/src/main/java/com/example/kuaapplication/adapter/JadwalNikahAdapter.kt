package com.example.kuaapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kuaapplication.R
import com.example.kuaapplication.model.DataJadwalNikah
import com.example.kuaapplication.model.DataJadwalPelatihan

class JadwalNikahAdapter (val models : ArrayList<DataJadwalNikah>) :
    RecyclerView.Adapter<JadwalNikahAdapter.ViewHolder>() {

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)  {
        var number = itemView.findViewById<TextView>(R.id.value_nomor)
        var tanggalPelatihan = itemView.findViewById<TextView>(R.id.value_tanggal)
        var jamNikah = itemView.findViewById<TextView>(R.id.value_jam)
        var lokasiPelatihan = itemView.findViewById<TextView>(R.id.value_lokasi)
        var penghulu = itemView.findViewById<TextView>(R.id.value_pelatih)
        var namaPria = itemView.findViewById<TextView>(R.id.value_nama_pria)
        var namaWanita = itemView.findViewById<TextView>(R.id.value_nama_wanita)
        var status = itemView.findViewById<TextView>(R.id.value_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal_nikah, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = (position + 1).toString()
        holder.tanggalPelatihan.text = models[position].tanggal
        holder.jamNikah.text = models[position].jam
        if (models[position].lokasi == null){
            holder.lokasiPelatihan.text = "null"
        }else{
            holder.lokasiPelatihan.text = models[position].lokasi.toString()
        }
        holder.penghulu.text = models[position].penghulu
        holder.namaPria.text = models[position].namaPria
        holder.namaWanita.text = models[position].namaWanita
        holder.status.text = models[position].status
    }

    override fun getItemCount(): Int {
        return models.size
    }
}