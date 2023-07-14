package com.example.kuaapplication.model

import com.google.gson.annotations.SerializedName

data class ResponseAddPemohon(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
