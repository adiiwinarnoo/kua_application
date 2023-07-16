package com.example.kuaapplication.model

import com.google.gson.annotations.SerializedName

data class ResponsePayment(

	@field:SerializedName("data")
	val data: DataPayment? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataPayment(

	@field:SerializedName("tanggal_bayar")
	val tanggalBayar: String? = null,

	@field:SerializedName("nominal")
	val nominal: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("url_file")
	val urlFile: String? = null,

	@field:SerializedName("catatan")
	val catatan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
