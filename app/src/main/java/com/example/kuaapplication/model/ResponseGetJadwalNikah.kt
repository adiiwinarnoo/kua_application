package com.example.kuaapplication.model

import com.google.gson.annotations.SerializedName

data class ResponseGetJadwalNikah(

	@field:SerializedName("data")
	val data: DataJadwalNikah? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataJadwalNikah(

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("jam")
	val jam: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("penghulu")
	val penghulu: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("nama_pria")
	val namaPria: String? = null,

	@field:SerializedName("nama_wanita")
	val namaWanita: String? = null
)
