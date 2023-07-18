package com.example.kuaapplication.model

import com.google.gson.annotations.SerializedName

data class ResponseGetJadwalPelatihan(

	@field:SerializedName("data")
	val data: DataJadwalPelatihan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataJadwalPelatihan(

	@field:SerializedName("nama_pelatih")
	val namaPelatih: String? = null,

	@field:SerializedName("jam")
	val jam: String? = null,

	@field:SerializedName("tempat_pelatihan")
	val tempatPelatih: Any? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
