package com.example.kuaapplication.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    )

    @FormUrlEncoded
    @POST("/api/register")
    fun register (
        @Field("name") name : String,
        @Field("username") userName : String,
        @Field("email") email : String,
        @Field("phone") phone : String,
        @Field("password") password : String,
        @Field("alamat") alamat : String,
        @Field("jenis_kelamin") jenisKelamin : String
    )


}